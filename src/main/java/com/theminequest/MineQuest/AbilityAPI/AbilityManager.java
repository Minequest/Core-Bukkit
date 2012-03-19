package com.theminequest.MineQuest.AbilityAPI;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.commons.ChatColor;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.AbilityRefreshedEvent;

public class AbilityManager implements Listener {
	
	private HashMap<String,Ability> abilities;
	
	public AbilityManager(){
		abilities = new HashMap<String,Ability>();
	}
	
	public void registerAbility(Ability a){
		abilities.put(a.getName(), a);
	}

	@EventHandler
	public void abilityRefreshed(AbilityRefreshedEvent e){
		e.getPlayer().sendMessage(
				ChatColor.GRAY+"Ability " + e.getAbility().getName() + " recharged!");
	}
	
	@EventHandler
	public void onPlayerEggThrowEvent(PlayerEggThrowEvent e){
		onEvent(e);
	}
	
	@EventHandler
	public void onPlayerFishEvent(PlayerFishEvent e){
		onEvent(e);
	}
	
	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e){
		onEvent(e);
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e){
		onEvent(e);
	}
	
	private void onEvent(PlayerEvent e){
		for (Ability a : abilities.values()){
			if (a.onEventCaught(e))
				return;
		}
	}
}
