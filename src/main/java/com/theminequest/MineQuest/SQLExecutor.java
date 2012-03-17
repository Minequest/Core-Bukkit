package com.theminequest.MineQuest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.DatabaseHandler;
import lib.PatPeter.SQLibrary.MySQL;
import lib.PatPeter.SQLibrary.SQLite;

import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;


public class SQLExecutor {
	
	private enum Mode{
		MySQL, SQlite;
	}
	private Mode databasetype;
	private DatabaseHandler db;
	private File datafolder;
	
	public SQLExecutor(){
		PropertiesFile config = MineQuest.configuration.getMainConfig();
		String dbtype = config.getString("databasetype");
		if (dbtype.equalsIgnoreCase("mysql"))
			databasetype = Mode.MySQL;
		else
			databasetype = Mode.SQlite;
		String hostname = config.getString("db_hostname","localhost");
		String port = config.getString("db_port","3306");
		String databasename = config.getString("db_name","minequest");
		String username = config.getString("db_username","root");
		String password = config.getString("db_password","toor");
		if (databasetype == Mode.MySQL)
			db = new MySQL(Logger.getLogger("Minecraft"),"mq_",hostname,port,databasename,username,password);
		else
			db = new SQLite(Logger.getLogger("Minecraft"),"mq_","minequest",MineQuest.activePlugin.getDataFolder().getAbsolutePath());
		datafolder = new File(MineQuest.activePlugin.getDataFolder().getAbsolutePath()+File.separator+"sql");
		checkInitialization();
	}

	private void checkInitialization() {
		if (!datafolder.exists()){
			datafolder.mkdir();
		}
		File versionfile = new File(datafolder+File.separator+"version");
		Scanner s = null;
		try {
			s = new Scanner(versionfile);
		} catch (FileNotFoundException e) {
			// ignore
		}
		if (s.nextLine().compareTo(MineQuest.getVersion())<0){
			// update SQL files
			try {
				FileUtils.cleanDirectory(datafolder);
				Downloader.download("http://www.theminequest.com/sql.zip", datafolder.getAbsolutePath());
				ZipUtils.unzip(datafolder.getAbsolutePath()+File.separator+"sql.zip", datafolder.getAbsolutePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	 * is not a {@code SELECT} query, this will ALWAYS return null.
	 * @param queryfilename sql filename to use
	 * @param params parameters for sql file
	 * @return ResultSet of SQL query (or null... if there really is nothing good.)
	 */
	public ResultSet querySQL(String queryfilename, String params){
		Scanner file = new Scanner(datafolder + File.separator + queryfilename + ".sql");
		while (file.hasNextLine()){
			String line = file.nextLine();
			// ignore comments
			if (!line.startsWith("#")){
				if (line.contains("%s"))
					line = line.replaceAll("%s", params);
				return db.query(line);
			}
		}
		return null;
	}
	
}
