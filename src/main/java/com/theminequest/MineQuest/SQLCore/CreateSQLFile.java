package com.theminequest.MineQuest.SQLCore;

import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.*;

public class CreateSQLFile {
	
	private Logger log;
	private String config;
	private MySQL mysql;
	private SQLite sqlite; 
	
	private String getConfig(){
		return config;
	}
	
	public boolean createsqlFile(String configuration, String hostName, String portNumber, String database, String username, String password){
		if(config == "Mysql"){
			config = configuration;
			this.mysql = new MySQL(this.log, "lib.PatPeter.SQLibrary", hostName, portNumber, database, username, password);
			this.mysql.open();			
			return true;
		}
		if(config == "SQLite"){
			sqlite = new SQLite(log, "lib.PatPeter.SQLibrary", "Minequest", "URL");
		}
		return false;
	}
	
	//Creates tables in the MySQL database.
	public boolean addTables(){
		if (this.mysql.checkConnection() == true){
			this.mysql.createTable("Player");
			this.mysql.createTable("QuestsList");
			this.mysql.createTable("Spells");
			this.mysql.createTable("Npcs");
			return true;
		}
		return false;
	}
	
	//Adds values to the table.
	public boolean addToTable(String query){
		if (getConfig() == "Mysql"){
			this.mysql.insertQuery(query);
			return true;
		}
		
		if (getConfig() == "SQLite"){
			this.sqlite.insertQuery(query);
			return true;
		}
		return false;
	}
}