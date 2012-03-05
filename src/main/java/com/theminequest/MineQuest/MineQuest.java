package com.theminequest.MineQuest;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.theminequest.MineQuest.Configuration.QuestConfig;
import com.theminequest.MineQuest.EventsAPI.EventManager;

public class MineQuest extends JavaPlugin {
	
	/*
	 * I NEED IT >_<
	 * But we could use Bukkit.getPluginManager().getPlugin("MineQuest")...
	 */
	public static MineQuest activePlugin = null;
	
	public static EventManager eventManager = null;
	
	public static QuestConfig configuration = null;
	
    private static PluginDescriptionFile description;
    
    public static void log(Level level, String msg){
   		Logger.getLogger("Minecraft").log(level, "[MineQuest] " + msg);
    }
    
    public static String getVersion() {
        return description.getVersion();
    }

    public static String getPluginName() {
        return description.getName();
    }
    
	@Override
	public void onEnable(){
		activePlugin = this;
		eventManager = new EventManager();
		configuration = new QuestConfig();
	}

	
}
