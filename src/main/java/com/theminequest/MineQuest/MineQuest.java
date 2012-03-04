package com.theminequest.MineQuest;

import org.bukkit.plugin.java.JavaPlugin;

public class MineQuest extends JavaPlugin {
	
	/*
	 * I NEED IT >_<
	 * But we could use Bukkit.getPluginManager().getPlugin("MineQuest")...
	 */
	public static MineQuest activePlugin = null;
	
	@Override
	public void onEnable(){
		activePlugin = this;
	}

	
}
