/**
 * This file, MineQuest.java, is part of MineQuest:
 * A full featured and customizable quest/mission system.
 * Copyright (C) 2012 The MineQuest Party
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

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.alta189.simplesave.exceptions.ConnectionException;
import com.theminequest.MQCoreEvents.RegisterEvents;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Edit.EditManager;
import com.theminequest.MineQuest.API.Events.EventManager;
import com.theminequest.MineQuest.API.Tracker.QuestStatistic;
import com.theminequest.MineQuest.API.Tracker.StatisticManager;
import com.theminequest.MineQuest.API.Utils.GriefcraftMetrics;
import com.theminequest.MineQuest.API.Utils.UtilManager;
import com.theminequest.MineQuest.Events.MQEventManager;
import com.theminequest.MineQuest.Frontend.Command.CommandListener;
import com.theminequest.MineQuest.Frontend.Command.PartyCommandFrontend;
import com.theminequest.MineQuest.Frontend.Command.QuestCommandFrontend;
import com.theminequest.MineQuest.Frontend.Sign.QuestSign;
import com.theminequest.MineQuest.Group.MQQuestGroupManager;
import com.theminequest.MineQuest.Quest.QuestManager;
import com.theminequest.MineQuest.Target.TargetManager;
import com.theminequest.MineQuest.Tasks.TaskManager;

/**
 * MineQuest Plugin Class for Bukkit
 * @author The MineQuest Party
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
	 * Access MineQuest configuration
	 */
	public static QuestConfig configuration = null;
	/**
	 * Access Hidendra's Metrics
	 */
	public static GriefcraftMetrics gcMetrics = null;
	private static PluginDescriptionFile description = null;
	
	/**
	 * Access main /minequest help menu modifier
	 */
	public static CommandListener commandListener = null;

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
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			getServer().getLogger().severe("================= MineQuest ==================");
			getServer().getLogger().severe("Vault is required for MineQuest to operate!");
			getServer().getLogger().severe("Please install Vault first!");
			getServer().getLogger().severe("You can find the latest version here:");
			getServer().getLogger().severe("http://dev.bukkit.org/server-mods/vault/");
			getServer().getLogger().severe("==============================================");
			setEnabled(false);
			return;
		}
		if (!getDataFolder().exists())
			getDataFolder().mkdirs();
		description = this.getDescription();
		Managers.setActivePlugin(this);
		if (description.getVersion().equals("unofficialDev")){
			Managers.log(Level.SEVERE,"[Core] You're using an unofficial dev build!");
			Managers.log(Level.SEVERE,"[Core] We cannot provide support for this unless you know the GIT hash.");
		}
		configuration = new QuestConfig();
		try {
			Statistics s = new Statistics();
			Managers.setStatisticManager(s);
			Managers.getStatisticManager().registerStatistic(QuestStatistic.class);
			getServer().getPluginManager().registerEvents(s, this);
		} catch (ConnectionException e1) {
			Managers.log(Level.SEVERE,"[Core] Can't start Statistic Manager!");
			e1.fillInStackTrace();
			e1.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(this);
		}
		Managers.setEditManager(new EditManager());
		getServer().getPluginManager().registerEvents(Managers.getEditManager(), this);
		Managers.setEventManager(new MQEventManager());
		getServer().getPluginManager().registerEvents(Managers.getEventManager(), this);
		Managers.setTaskManager(new TaskManager());
		getServer().getPluginManager().registerEvents(Managers.getTaskManager(), this);
		Managers.setQuestGroupManager(new MQQuestGroupManager());
		Managers.setGroupManager(Managers.getQuestGroupManager());
		getServer().getPluginManager().registerEvents(Managers.getGroupManager(), this);
		Managers.setQuestManager(new QuestManager());
		getServer().getPluginManager().registerEvents(Managers.getQuestManager(), this);
		Managers.setUtilManager(new UtilManager());
		getServer().getPluginManager().registerEvents(Managers.getUtilManager(), this);
		Managers.setTargetManager(new TargetManager());
		try {
			gcMetrics = new GriefcraftMetrics(this);
			gcMetrics.start();
		} catch (IOException e) {
			Managers.log(Level.WARNING, "[Metrics] Could not attach to Hidendra Metrics.");
		}
		
		if (!setupPermissions())
			Managers.log(Level.SEVERE,"[Vault] You don't seem to have any permissions plugin...");
		if (!setupEconomy())
			Managers.log(Level.SEVERE,"[Vault] You don't seem to have any economy plugin...");
		RegisterEvents.registerEvents();

		// sign frontend
		getServer().getPluginManager().registerEvents(new QuestSign(), this);
		// command frontend
		commandListener = new CommandListener();
		getCommand("minequest").setExecutor(commandListener);
		getCommand("quest").setExecutor(new QuestCommandFrontend());
		getCommand("party").setExecutor(new PartyCommandFrontend());
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			@Override
			public void run() {
				Managers.getQuestManager().reloadQuests();
				try {
					Managers.getStatisticManager().connect(true);
				} catch (ConnectionException e) {
					Managers.log(Level.SEVERE,"[Core] Can't start Statistic Manager!");
					e.fillInStackTrace();
					e.printStackTrace();
					Bukkit.getPluginManager().disablePlugin(Managers.getActivePlugin());
				}
			}
		});

	}

	@Override
	public void onDisable() {
		try {
			StatisticManager statisticManager = Managers.getStatisticManager();
			if (statisticManager != null)
				statisticManager.connect(false);
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		description = null;
		Managers.setActivePlugin(null);
		EventManager eventManager = Managers.getEventManager();
		if (eventManager != null) {
			Managers.getEventManager().dismantleRunnable();
			Managers.setEventManager(null);
		}
		commandListener = null;
		Managers.setEditManager(null);
		Managers.setTaskManager(null);
		Managers.setQuestManager(null);
		Managers.setGroupManager(null);
		Managers.setUtilManager(null);
		configuration = null;
		permission = null;
		economy = null;
	}
}
