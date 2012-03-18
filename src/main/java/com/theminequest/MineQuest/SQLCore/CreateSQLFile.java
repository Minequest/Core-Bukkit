package com.theminequest.MineQuest.SQLCore;

import java.util.logging.Logger;

import org.bukkit.Bukkit;

import lib.PatPeter.SQLibrary.*;

public class CreateSQLFile {
	
	private Logger log;
	private String config;
	private MySQL mysql;
	private SQLite sqlite; 
	private String playerInsert;
	
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
			sqlite = new SQLite(log, "lib.PatPeter.SQLibrary", "Minequest", Bukkit.getPluginManager().getPlugin("Minequest").getDataFolder().getPath());
		}
		return false;
	}
	
	//Creates tables in the MySQL database.
	public boolean addTables(){
		if (this.mysql.checkConnection() == true){
			this.mysql.createTable("CREATE TABLE Player (ID char(6), Name char(25), ClassName char(10) default 'none', Level char(6) default '1', Exp char(10) default '1')");
			this.mysql.createTable("CREATE TABLE QuestList (ID char(6), Name char(25), QuestName char(25), Completed char(1) default 'n')");
			this.mysql.createTable("CREATE TABLE Npc (ID char(6), Name char(25), Class char(10), Health char(20), Vulnerable char(1) default 'n')");
			this.mysql.createTable("CREATE TABLE Spells (ID char(6), Name char(25), SpellName char(25)");
			return true;
		}
		return false;
	}
	
	//Player Convert to query
	public void PlayerQuery(String table, double id, String name, String className, String level, Double exp){
		addToTable("INSERT INTO Players (ID, Name, ClassName, Level, Exp");
		playerInsert = ("VALUES (" + id + ", " + name + ", " + className + ", " + level + ", " + exp);
		addToTable(playerInsert);
	}
	
	//Adds values to the table.
	public void addToTable(String query){
		if (getConfig() == "Mysql"){
			this.mysql.insertQuery(query);
		}
		
		if (getConfig() == "SQLite"){
			this.sqlite.insertQuery(query);
		}
	}
}
