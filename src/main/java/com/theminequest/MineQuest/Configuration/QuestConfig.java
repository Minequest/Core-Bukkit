package com.theminequest.MineQuest.Configuration;

import java.io.File;
import java.util.logging.Level;

import lib.PatPeter.SQLibrary.DatabaseHandler;

import com.theminequest.MineQuest.MineQuest;

public final class QuestConfig {

	private PropertiesFile mainConfig;
	private PropertiesFile economyConfig;
	private DatabaseHandler databaseStorage;
	
	public QuestConfig(){
		MineQuest.log(Level.INFO, "Loading configuration...");
		/*
		 * Load Main Configuration
		 */
		mainConfig = new PropertiesFile(MineQuest.activePlugin.getDataFolder()
				+File.separator+"config.properties");
		
	}
	
	public enum Main {
		
	}
	
}
