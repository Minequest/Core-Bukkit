/**
 * This file, MineQuest.java, is part of MineQuest:
 * A full featured and customizable quest/mission system.
 * Copyright (C) 2012 The MineQuest Team
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 **/
package com.theminequest.MineQuest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.theminequest.MQCoreEvents.RegisterEvents;
import com.theminequest.MineQuest.Editable.EditManager;
import com.theminequest.MineQuest.EventsAPI.EventManager;
import com.theminequest.MineQuest.Frontend.Command.CommandListener;
import com.theminequest.MineQuest.Frontend.Command.PartyCommandFrontend;
import com.theminequest.MineQuest.Frontend.Command.QuestCommandFrontend;
import com.theminequest.MineQuest.Frontend.Sign.QuestSign;
import com.theminequest.MineQuest.Group.GroupManager;
import com.theminequest.MineQuest.Quest.QuestManager;
import com.theminequest.MineQuest.Tasks.TaskManager;
import com.theminequest.MineQuest.Utils.GriefcraftMetrics;
import com.theminequest.MineQuest.Utils.UtilManager;

/**
 * MineQuest Plugin Class for Bukkit
 * @author The MineQuest Team
 *
 */
public class MineQuest extends JavaPlugin {

	/**
	 * Access Permissions via Vault
	 */
	public static Permission permission = null;
	/**
	 * Access Economy via Vault
	 */
	public static Economy economy = null;
	/**
	 * Access MineQuest Plugin Methods
	 */
	public static MineQuest activePlugin = null;
	/**
	 * Access MineQuest editManager
	 */
	public static EditManager editManager = null;
	/**
	 * Access MineQuest eventManager
	 */
	public static EventManager eventManager = null;
	/**
	 * Access MineQuest taskManager
	 */
	public static TaskManager taskManager = null;
	/**
	 * Access MineQuest questManager
	 */
	public static QuestManager questManager = null;
	/**
	 * Access MineQuest groupManager
	 */
	public static GroupManager groupManager = null;
	/**
	 * Access MineQuest utilities
	 */
	public static UtilManager utilManager = null;
	/**
	 * Access MineQuest configuration
	 */
	public static QuestConfig configuration = null;
	/**
	 * Access MineQuest SQL database
	 */
	public static SQLExecutor sqlstorage = null;
	/**
	 * Access Hidendra's Metrics
	 */
	public static GriefcraftMetrics gcMetrics = null;
	private static PluginDescriptionFile description = null;

	/**
	 * Log using the central MineQuest logger.
	 * (Prefixed with <code>[MineQuest]</code>; to add on component, add prefix to message.)
	 * @param msg Message to log with level <code>INFO</code>.
	 */
	public static void log(String msg) {
		log(Level.INFO, msg);
	}

	/**
	 * Log using the central MineQuest logger.
	 * (Prefixed with <code>[MineQuest]</code>; to add on component, add prefix to message.)
	 * @param level Level to log with.
	 * @param msg Message to log.
	 */
	public static void log(Level level, String msg) {
		Logger.getLogger("Minecraft").log(level, "[MineQuest] " + msg);
	}

	/**
	 * Get this build version of MineQuest.
	 * @return Build version.
	 */
	public static String getVersion() {
		return description.getVersion();
	}

	/**
	 * Get this central plugin name.
	 * @return Name.
	 */
	public static String getPluginName() {
		return description.getName();
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}
		return (economy != null);
	}

	@Override
	public void onEnable() {
		if (!getDataFolder().exists())
			getDataFolder().mkdirs();
		description = this.getDescription();
		activePlugin = this;
		if (description.getVersion().equals("unofficialDev")){
			MineQuest.log(Level.SEVERE,"[Core] You're using an unofficial dev build!");
			MineQuest.log(Level.SEVERE,"[Core] I CANNOT keep track of changes with this.");
		}
		configuration = new QuestConfig();
		sqlstorage = new SQLExecutor();
		editManager = new EditManager();
		getServer().getPluginManager().registerEvents(editManager, this);
		eventManager = new EventManager();
		getServer().getPluginManager().registerEvents(eventManager, this);
		taskManager = new TaskManager();
		getServer().getPluginManager().registerEvents(taskManager, this);
		groupManager = new GroupManager();
		getServer().getPluginManager().registerEvents(groupManager, this);
		questManager = new QuestManager();
		getServer().getPluginManager().registerEvents(questManager, this);
		utilManager = new UtilManager();
		getServer().getPluginManager().registerEvents(utilManager, this);
		try {
			gcMetrics = new GriefcraftMetrics(this);
			gcMetrics.start();
		} catch (IOException e) {
			log(Level.WARNING, "[Metrics] Could not attach to Hidendra Metrics.");
		}
		
		if (!setupPermissions())
			log(Level.SEVERE,"[Vault] You don't seem to have any permissions plugin...");
		if (!setupEconomy())
			log(Level.SEVERE,"[Vault] You don't seem to have any economy plugin...");
		RegisterEvents.registerEvents();

		// sign frontend
		getServer().getPluginManager().registerEvents(new QuestSign(), this);
		// command frontend
		CommandListener commandFrontend = new CommandListener();
		getCommand("minequest").setExecutor(commandFrontend);
		getCommand("quest").setExecutor(new QuestCommandFrontend());
		getCommand("party").setExecutor(new PartyCommandFrontend());
	}

	@Override
	public void onDisable() {
		description = null;
		activePlugin = null;
		eventManager.dismantleRunnable();
		eventManager = null;
		editManager = null;
		taskManager = null;
		questManager = null;
		groupManager = null;
		utilManager = null;
		configuration = null;
		sqlstorage = null;
		permission = null;
		economy = null;
	}
}
