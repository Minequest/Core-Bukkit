package com.theminequest.MineQuest.Ability;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.commons.ChatColor;

import com.theminequest.MineQuest.MineQuest;

public class AbilityManager implements Listener {
	
	private HashMap<String,Ability> abilities;
	
	public AbilityManager(){
		abilities = new HashMap<String,Ability>();
	}
	
	public void registerAbility(Ability a){
		Bukkit.getPluginManager().registerEvents(a, MineQuest.activePlugin);
		abilities.put(a.getName(), a);
	}

	@EventHandler
	public void abilityRefreshed(AbilityRefreshedEvent e){
		e.getPlayer().sendMessage(
				ChatColor.GRAY+"Ability " + e.getAbility().getName() + " recharged!");
	}
}
