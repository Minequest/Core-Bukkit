package com.theminequest.MineQuest;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.theminequest.MineQuest.Ability.AbilityManager;
import com.theminequest.MineQuest.Configuration.QuestConfig;
import com.theminequest.MineQuest.EventsAPI.EventManager;
import com.theminequest.MineQuest.Player.PlayerManager;
import com.theminequest.MineQuest.Quest.QuestManager;
import com.theminequest.MineQuest.Tasks.TaskManager;
import com.theminequest.MineQuest.Team.TeamManager;

public class MineQuest extends JavaPlugin {
	
	/*
	 * I NEED IT >_<
	 * But we could use Bukkit.getPluginManager().getPlugin("MineQuest")...
	 */
	public static Permission permission = null;
	
	public static Economy economy = null;
	
	public static MineQuest activePlugin = null;
	
	public static AbilityManager abilityManager = null;
	
	public static EventManager eventManager = null;
	
	public static TaskManager taskManager = null;
	
	public static QuestManager questManager = null;
	
	public static PlayerManager playerManager = null;
	
	public static TeamManager teamManager = null;
	
	public static QuestConfig configuration = null;
	
    private static PluginDescriptionFile description;
    
    public static void log(String msg){
    	log(Level.INFO,msg);
    }
    
    public static void log(Level level, String msg){
   		Logger.getLogger("Minecraft").log(level, "[MineQuest] " + msg);
    }
    
    public static String getVersion() {
        return description.getVersion();
    }

    public static String getPluginName() {
        return description.getName();
    }
    
    public Boolean setupPermissions(){
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
	    	 permission = permissionProvider.getProvider();
	     }
	     return (permission != null);
    }

	public Boolean setupEconomy(){
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null){
            economy = economyProvider.getProvider();
        }
        return (economy != null);
	}

	@Override
	public void onEnable(){
		activePlugin = this;
		abilityManager = new AbilityManager();
		eventManager = new EventManager();
		taskManager = new TaskManager();
		getServer().getPluginManager().registerEvents(taskManager, this);
		questManager = new QuestManager();
		getServer().getPluginManager().registerEvents(questManager, this);
		playerManager = new PlayerManager();
		getServer().getPluginManager().registerEvents(playerManager, this);
		teamManager = new TeamManager();
		getServer().getPluginManager().registerEvents(teamManager, this);
		configuration = new QuestConfig();
	}	
	
	@Override
	public void onDisable(){
		// null out all static variables.
	}
}