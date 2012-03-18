package com.theminequest.MineQuest;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.theminequest.MineQuest.AbilityAPI.AbilityManager;
import com.theminequest.MineQuest.Editable.EditableManager;
import com.theminequest.MineQuest.EventsAPI.EventManager;
import com.theminequest.MineQuest.Player.PlayerManager;
import com.theminequest.MineQuest.Quest.QuestManager;
import com.theminequest.MineQuest.Tasks.TaskManager;
import com.theminequest.MineQuest.Team.TeamManager;

public class MineQuest extends JavaPlugin {

	/*
	 * I NEED IT >_< But we could use
	 * Bukkit.getPluginManager().getPlugin("MineQuest")...
	 */
	public static Permission permission = null;
	public static Economy economy = null;
	public static MineQuest activePlugin = null;
	public static AbilityManager abilityManager = null;
	public static EventManager eventManager = null;
	public static EditableManager editableManager = null;
	public static TaskManager taskManager = null;
	public static QuestManager questManager = null;
	public static PlayerManager playerManager = null;
	public static TeamManager teamManager = null;
	public static QuestConfig configuration = null;
	public static SQLExecutor sqlstorage = null;
	private static PluginDescriptionFile description = null;;

	public static void log(String msg) {
		log(Level.INFO, msg);
	}

	public static void log(Level level, String msg) {
		Logger.getLogger("Minecraft").log(level, "[MineQuest] " + msg);
	}

	public static String getVersion() {
		return description.getVersion();
	}

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
		configuration = new QuestConfig();
		sqlstorage = new SQLExecutor();
		abilityManager = new AbilityManager();
		getServer().getPluginManager().registerEvents(abilityManager, this);
		eventManager = new EventManager();
		editableManager = new EditableManager();
		getServer().getPluginManager().registerEvents(editableManager, this);
		taskManager = new TaskManager();
		getServer().getPluginManager().registerEvents(taskManager, this);
		questManager = new QuestManager();
		getServer().getPluginManager().registerEvents(questManager, this);
		playerManager = new PlayerManager();
		getServer().getPluginManager().registerEvents(playerManager, this);
		teamManager = new TeamManager();
		getServer().getPluginManager().registerEvents(teamManager, this);
		if (!setupPermissions())
			log(Level.SEVERE,"Permissions could not be setup!");
		if (!setupEconomy())
			log(Level.SEVERE,"Economy could not be setup!");
	}

	@Override
	public void onDisable() {
		playerManager.saveAll();
		description = null;
		activePlugin = null;
		abilityManager = null;
		eventManager = null;
		editableManager = null;
		taskManager = null;
		questManager = null;
		playerManager = null;
		teamManager = null;
		configuration = null;
		sqlstorage = null;
		permission = null;
		economy = null;
	}
}