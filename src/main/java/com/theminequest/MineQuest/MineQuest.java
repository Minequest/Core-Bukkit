package com.theminequest.MineQuest;

import org.bukkit.plugin.java.JavaPlugin;

import com.theminequest.MineQuest.EventsAPI.EventManager;

public class MineQuest extends JavaPlugin {
	
	/*
	 * I NEED IT >_<
	 * But we could use Bukkit.getPluginManager().getPlugin("MineQuest")...
	 */
	public static MineQuest activePlugin = null;
	
	public static EventManager eventManager = null;
	
	@Override
	public void onEnable(){
		activePlugin = this;
		eventManager = new EventManager();
	}

	
}
