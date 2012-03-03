package com.theminequest.MineQuest.Configuration;

import java.io.File;
import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.DatabaseHandler;
import lib.PatPeter.SQLibrary.MySQL;
import lib.PatPeter.SQLibrary.SQLite;

import org.bukkit.plugin.java.JavaPlugin;

public class SQLDatabase {

	private JavaPlugin plugin;
	private enum Mode{
		MySQL, SQlite;
	}
	private Mode databasetype;
	private DatabaseHandler db;
	
	public SQLDatabase(JavaPlugin p){
		plugin = p;
		PropertiesFile config = new PropertiesFile(p.getDataFolder()+File.separator+"config.properties");
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
			db = new SQLite(Logger.getLogger("Minecraft"),"mq_","minequest",plugin.getDataFolder().getAbsolutePath());
	}

	private void checkInitialization() {
		// TODO Auto-generated method stub
		
	}
	
	public DatabaseHandler getDB(){
		return db;
	}

	
}
