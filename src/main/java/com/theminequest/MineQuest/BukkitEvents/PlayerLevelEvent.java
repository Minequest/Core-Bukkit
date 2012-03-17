package com.theminequest.MineQuest.BukkitEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLevelEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	private Player player;
	
	public PlayerLevelEvent(Player p){
		player = p;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
}
