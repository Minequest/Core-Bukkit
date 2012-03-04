package com.theminequest.MineQuest;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.theminequest.MineQuest.EventsAPI.EventManager;

public class MineQuest extends JavaPlugin {
	
	/*
	 * I NEED IT >_<
	 * But we could use Bukkit.getPluginManager().getPlugin("MineQuest")...
	 */
	public static MineQuest activePlugin = null;
	
	public static EventManager eventManager = null;
	
    private static PluginDescriptionFile description;
    
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

	}

	
}
