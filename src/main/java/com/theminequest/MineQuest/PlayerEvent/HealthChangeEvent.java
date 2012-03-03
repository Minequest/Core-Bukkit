package com.theminequest.MineQuest.PlayerEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import com.theminequest.MineQuest.Quest.Quest;

import com.theminequest.MineQuest.QuestEvent.QuestTypicalEvent;

public class HealthChangeEvent {
	
	private static final HandlerList handlers = new HandlerList();
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	private Player players;
	private int changeby;

	public HealthChangeEvent(Player p, int c) {
		players = p;
		changeby = c;
	}
	
	public int getChangeBy(){
		return changeby;
	}
	
	public Player getPlayers(){
		return players;
	}

}
