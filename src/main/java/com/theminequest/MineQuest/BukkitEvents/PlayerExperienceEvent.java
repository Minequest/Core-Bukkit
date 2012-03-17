package com.theminequest.MineQuest.BukkitEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerExperienceEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	private Player player;
	private int experience;
	
	public PlayerExperienceEvent(Player p, int expchange){
		player = p;
		experience = expchange;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public int getExperienceChange(){
		return experience;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
}
