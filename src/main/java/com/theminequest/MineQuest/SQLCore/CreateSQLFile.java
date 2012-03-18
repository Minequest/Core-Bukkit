package com.theminequest.MineQuest.SQLCore;

import lib.PatPeter.SQLibrary.*;

public class CreateSQLFile {
	
	private String config;
	private MySQL mysql;
	private SQLite sqlite; 
	private String playerInsert;
	
	private String getConfig(){
		return config;
	}
	
	//Creates tables in the MySQL database.
	public boolean addTables(String config){
		if (config == "Mysql"){
			if (this.mysql.checkConnection() == true){
				this.mysql.createTable("CREATE TABLE Player (ID char(6), Name char(25), ClassName char(10) default 'none', Level char(6) default '1', Exp char(10) default '1')");
				this.mysql.createTable("CREATE TABLE QuestList (ID char(6), Name char(25), QuestName char(25), Completed char(1) default 'n')");
				this.mysql.createTable("CREATE TABLE Npc (ID char(6), Name char(25), Class char(10), Health char(20), Vulnerable char(1) default 'n')");
				this.mysql.createTable("CREATE TABLE Spells (ID char(6), Name char(25), SpellName char(25)");
				return true;
			}
		}
		if (config == "Sqlite"){
			if (this.sqlite.checkConnection() == true){
				this.sqlite.createTable("CREATE TABLE Player (ID char(6), Name char(25), ClassName char(10) default 'none', Level char(6) default '1', Exp char(10) default '1')");
				this.sqlite.createTable("CREATE TABLE QuestList (ID char(6), Name char(25), QuestName char(25), Completed char(1) default 'n')");
				this.sqlite.createTable("CREATE TABLE Npc (ID char(6), Name char(25), Class char(10), Health char(20), Vulnerable char(1) default 'n')");
				this.sqlite.createTable("CREATE TABLE Spells (ID char(6), Name char(25), SpellName char(25)");
				return true;
			}
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
			this.mysql.simpleQuery(query);
		}
		
		if (getConfig() == "SQLite"){
			this.sqlite.simpleQuery(query);
		}
	}
}