package com.theminequest.MineQuest;

import java.io.File;
import java.util.logging.Level;

public final class QuestConfig {

	public PropertiesFile mainConfig;
	public PropertiesFile questConfig;
	public PropertiesFile economyConfig;
	public PropertiesFile databaseConfig;
	
	public QuestConfig(){
		MineQuest.log(Level.INFO, "Loading configuration...");
		String basefolder = MineQuest.activePlugin.getDataFolder()+File.separator;
		/*
		 * Load Main Configuration
		 */
		mainConfig = new PropertiesFile(basefolder+"config.properties");
		/*
		 * Load Quest Configuration
		 */
		questConfig = new PropertiesFile(basefolder+"quest.properties");
		/*
		 * Load Economy Hookin Configuration
		 */
		economyConfig = new PropertiesFile(basefolder+"economy.properties");
		/*
		 * Load Database Configuration
		 */
		databaseConfig = new PropertiesFile(basefolder+"database.properties");
	}
	
}
