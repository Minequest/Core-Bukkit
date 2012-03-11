package com.theminequest.MineQuest.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener{
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerRespawn(final PlayerRespawnEvent event){
		final Player player = event.getPlayer();
		//Remove player from team.
		
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public Location onPlayerMove(final PlayerMoveEvent event){
		//Check if player is on quest
		final Location newLocation = event.getTo();
		
		return newLocation;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(final PlayerQuitEvent event){
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(final PlayerJoinEvent event){
		//Not sure if we need this in the core. Maybe for the abilities. 
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerLogin(final PlayerLoginEvent event){
		//Not sure if we need this in the core. Maybe for the abilities. 
	}
}
