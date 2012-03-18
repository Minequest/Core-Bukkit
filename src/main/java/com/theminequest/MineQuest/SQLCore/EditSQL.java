package com.theminequest.MineQuest.SQLCore;

import java.sql.ResultSet;
import java.sql.SQLException;

import lib.PatPeter.SQLibrary.MySQL;
import lib.PatPeter.SQLibrary.SQLite;

import com.theminequest.MineQuest.MineQuest;

public class EditSQL {
	private MySQL mysql;
	private SQLite sqlite; 
	boolean connection = this.mysql.checkConnection();

	
	public void addExp(String playerName, double exp){
		ResultSet rs = this.mysql.query("SELECT Name, Level, Exp from Players");
		try {
			while (rs.next()) {
				String name = rs.getString("Name");
				double currentlvl = rs.getDouble("Level");
				double currentExp = rs.getDouble("Exp");
				if (name == playerName){
					double newExp = currentExp + exp;
					if (newExp == 1000){
						currentlvl = currentlvl + 1;
						newExp = newExp - 1000;
						this.mysql.simpleQuery("UPDATE Players SET Name, Level, Exp WHERE Name="+playerName);
					}
				}	
			}
		} catch (SQLException e) {
			MineQuest.log("Could not update minequest sql for player: " + playerName);
		}
	} 
}
