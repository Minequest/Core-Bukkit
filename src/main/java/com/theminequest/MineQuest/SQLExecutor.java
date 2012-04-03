/**
 * This file, SQLExecutor.java, is part of MineQuest:
 * A full featured and customizable quest/mission system.
 * Copyright (C) 2012 The MineQuest Team
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 **/
package com.theminequest.MineQuest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.theminequest.MineQuest.Utils.PropertiesFile;

import lib.PatPeter.SQLibrary.DatabaseHandler;
import lib.PatPeter.SQLibrary.H2;
import lib.PatPeter.SQLibrary.MySQL;
import lib.PatPeter.SQLibrary.SQLite;

public class SQLExecutor {

	private enum Mode{
		MySQL, SQlite, H2;
	}
	private Mode databasetype;
	private DatabaseHandler db;
	private File datafolder;

	public SQLExecutor(){
		MineQuest.log("[SQL] Loading and connecting to SQL...");
		PropertiesFile config = MineQuest.configuration.databaseConfig;
		String dbtype = config.getString("db_type","h2");
		if (dbtype.equalsIgnoreCase("mysql"))
			databasetype = Mode.MySQL;
		else if (dbtype.equalsIgnoreCase("sqlite"))
			databasetype = Mode.SQlite;
		else
			databasetype = Mode.H2;
		MineQuest.log("[SQL] Using "+databasetype.name()+" as database.");
		String hostname = config.getString("db_hostname","localhost");
		String port = config.getString("db_port","3306");
		String databasename = config.getString("db_name","minequest");
		String username = config.getString("db_username","root");
		String password = config.getString("db_password","toor");
		if (databasetype == Mode.MySQL)
			db = new MySQL(Logger.getLogger("Minecraft"),"[MineQuest] [SQL] ",hostname,port,databasename,username,password);
		else if (databasetype == Mode.SQlite)
			db = new SQLite(Logger.getLogger("Minecraft"),"[MineQuest] [SQL] ","minequest",MineQuest.activePlugin.getDataFolder().getAbsolutePath());
		else
			db = new H2(Logger.getLogger("Minecraft"),"[MineQuest] [SQL] ","minequest",MineQuest.activePlugin.getDataFolder().getAbsolutePath());
		datafolder = new File(MineQuest.activePlugin.getDataFolder().getAbsolutePath()+File.separator+"sql");
		checkInitialization();
	}

	private void checkInitialization() {
		if (!datafolder.exists()){
			datafolder.mkdir();
		}
		File versionfile = new File(datafolder+File.separator+"version");
		Scanner s = null;
		String lastv = null;
		try {
			s = new Scanner(versionfile);
			if (s.hasNextLine()){
				String dbtype = s.nextLine();
				if (dbtype.equalsIgnoreCase(databasetype.name())){
					if (s.hasNextLine())
						lastv = s.nextLine();
				}
			}
		} catch (FileNotFoundException e) {
			try {
				versionfile.createNewFile();
			} catch (IOException e1) {
				throw new RuntimeException(e1);
			}
		}
		if (lastv==null || lastv.compareTo(MineQuest.getVersion())!=0){
			if (lastv==null || lastv.equals("unofficialDev")){
				if (lastv==null)
					MineQuest.log(Level.WARNING, "[SQL] No existing DBVERSION file; initializing DB as new.");
				else
					MineQuest.log(Level.WARNING, "[SQL] I don't know what your previous build was; attempting to reinitializing.");
				lastv = "initial";
			}
			
			if (lastv!=null && !lastv.equals("unofficialDev")){
				int last = Integer.parseInt(lastv);
				MineQuest.log(Level.INFO, "[SQL] Fast forwarding through builds...");
				while (last<Integer.parseInt(MineQuest.getVersion())){
					try {
						MineQuest.log("[SQL] Fast forwarding through build " + last + "...");
						querySQL("update/"+last,"");
					} catch (NoSuchElementException e){
						// ignore
					}
					last++;
				}
			} else {
				try {
					querySQL("update/"+lastv,"");
				} catch (NoSuchElementException e){
					MineQuest.log(Level.WARNING,"[SQL] No update path from build " + lastv + " to this build; Probably normal.");
				}
			}
			
			Writer out;
			try {
				out = new OutputStreamWriter(new FileOutputStream(versionfile));
				out.write(databasetype.name()+IOUtils.LINE_SEPARATOR+MineQuest.getVersion());
				out.close();
			} catch (FileNotFoundException e) {
				MineQuest.log(Level.SEVERE,"[SQL] Failed to commit DBVERSION: " + e.getMessage());
				throw new RuntimeException(e);
			} catch (IOException e) {
				MineQuest.log(Level.SEVERE,"[SQL] Failed to commit DBVERSION: " + e.getMessage());
				throw new RuntimeException(e);
			}
		}

	}

	public DatabaseHandler getDB(){
		return db;
	}

	/**
	 * Query an SQL and return a {@link java.sql.ResultSet} of the result.
	 * If the SQL file contains {@code %s} in the query, the parameters
	 * specified will replace {@code %s} in the query. Remember that if the query
	 * is not a {@code SELECT, EXPLAIN, CALL, SCRIPT, SHOW, HELP} query, this will ALWAYS return null.
	 * @param queryfilename sql filename to use
	 * @param params parameters for sql file
	 * @return ResultSet of SQL query (or null... if there really is nothing good.)
	 */
	public ResultSet querySQL(String queryfilename, String ...params) {
		InputStream i = MineQuest.activePlugin.getResource("sql/"+queryfilename+".sql");
		if (i==null)
			throw new NoSuchElementException("No such resource: " + queryfilename + ".sql");
		String[] filecontents = convertStreamToString(i).split(IOUtils.LINE_SEPARATOR);
		for (String line : filecontents){
			// ignore comments
			if (!line.startsWith("#") && !line.equals("")){
				if (params!=null && params.length!=0){
					int paramsposition = 0;
					while (paramsposition<params.length && line.contains("%s")){
						line = line.replaceFirst("%s", params[paramsposition]);
						paramsposition++;
					}
				}
				ResultSet result = db.query(line);
				if (result!=null)
					return result;
			}
		}
		return null;
	}
	
	/**
	 * Get a certain column from a ResultSet
	 * @param rs ResultSet
	 * @param columnname Column Name
	 * @return list of <b>Strings</b> containing the contents of the column.
	 * @throws SQLException If the ResultSet cannot be queried.
	 */
	public List<String> getColumn(ResultSet rs, String columnname) throws SQLException {
		if (rs==null)
			throw new IllegalArgumentException("ResultSet cannot be null!");
		List<String> toreturn = new ArrayList<String>();
		try {
		if (!rs.first())
			return toreturn;
		do {
			toreturn.add(rs.getString(columnname));
		}while (rs.next());
		return toreturn;
		} catch (SQLException e) {
			MineQuest.log(Level.SEVERE, "[SQL] SQL exception on ResultSet: " + e);
			throw e;
		}
	}

	private String convertStreamToString(InputStream is) {
		try {
			return IOUtils.toString(is);
		} catch (IOException e) {
			return null;
		}
	}

}
