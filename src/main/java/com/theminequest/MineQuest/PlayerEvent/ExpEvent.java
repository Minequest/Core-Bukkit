package com.theminequest.MineQuest.PlayerEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ExpEvent extends Event{
	
	private static final HandlerList handlers = new HandlerList();
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	private Player players;
	private int experience;
	
	/**
	 * Trigger an Experience event (gain/lose exp)
	 * @param p player to affect
	 * @param e experience to gain/lose
	 */
	public ExpEvent(Player p, int e){
		players = p;
		experience = e;
	}
	
	public int getExperience(){
		return experience;
	}
	
	public Player getPlayers(){
		return players;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	

}
