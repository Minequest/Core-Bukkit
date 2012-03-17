package SqlCore;

import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.*;

public class CreateSQLFile {
	
	private Logger log;
	
	private static MySQL mysql;
	private static SQLite sqlite; 
	
	public boolean createsqlFile(String config, String hostName, String portNumber, String database, String username, String password){
		if(config == "Mysql"){
			mysql = new MySQL(this.log, "lib.PatPeter.SQLibrary", hostName, portNumber, database, username, password);
			mysql.open();
			if (mysql.checkConnection() == true){
				mysql.createTable("Player");
				mysql.createTable("QuestsList");
				mysql.createTable("Spells");
				mysql.createTable("Npcs");
				
			}
			
			
			return true;
		}
		if(config == "SQLite"){
			new SQLite(log, "lib.PatPeter.SQLibrary", "Minequest", "URL");
		}
		return false;
	}
	public boolean addTable(String tableName, double width){
		
	}
}
