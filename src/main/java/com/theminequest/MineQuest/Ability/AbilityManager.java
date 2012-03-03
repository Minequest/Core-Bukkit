package com.theminequest.MineQuest.Ability;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AbilityManager {
	
	private static HashMap<String,Ability> abilities = new HashMap<String,Ability>();
	
	public static void registerAbility(JavaPlugin p, Ability a){
		Bukkit.getPluginManager().registerEvents(a, p);
		abilities.put(a.getName(), a);
	}

}
