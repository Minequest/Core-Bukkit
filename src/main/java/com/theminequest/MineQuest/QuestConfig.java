package com.theminequest.MineQuest;

import java.io.File;
import java.util.logging.Level;

import lib.PatPeter.SQLibrary.DatabaseHandler;


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

	public PropertiesFile getMainConfig() {
		return mainConfig;
	}
	
}
