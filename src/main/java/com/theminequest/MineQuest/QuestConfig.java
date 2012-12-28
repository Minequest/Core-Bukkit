/*
 * This file is part of MineQuest, The ultimate MMORPG plugin!.
 * MineQuest is licensed under GNU General Public License v3.
 * Copyright (C) 2012 The MineQuest Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.theminequest.MineQuest;

import java.io.File;
import java.util.logging.Level;

import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Utils.PropertiesFile;

public final class QuestConfig {
	
	public final PropertiesFile mainConfig;
	public final PropertiesFile groupConfig;
	public final PropertiesFile questConfig;
	public final PropertiesFile databaseConfig;
	
	public QuestConfig() {
		Managers.log(Level.INFO, "Loading configuration...");
		String basefolder = Managers.getActivePlugin().getDataFolder() + File.separator;
		/*
		 * Load Main Configuration
		 */
		mainConfig = new PropertiesFile(basefolder + "config.properties");
		/*
		 * Load Group Configuration
		 */
		groupConfig = new PropertiesFile(basefolder + "groups.properties");
		/*
		 * Load Quest Configuration
		 */
		questConfig = new PropertiesFile(basefolder + "quest.properties");
		/*
		 * Load Database Configuration
		 */
		databaseConfig = new PropertiesFile(basefolder + "database.properties");
	}
	
}
