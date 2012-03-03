package com.theminequest.MineQuest.QuestEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.monksanctum.MineQuest.Quest.Quest;


public abstract class QuestTypicalEvent extends Event implements QuestEvent {

	private static final HandlerList handlers = new HandlerList();
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	private Player[] players;
	private Quest quest;
	
	/**
	 * Typical Quest Event
	 * @param p Players involved
	 * @param q Quest involved
	 */
	public QuestTypicalEvent(Player[] p, Quest q){
		players = p;
		quest = q;
	}
	
	public Player[] getPlayers() {
		return players;
	}

	@Override
	public Quest getQuest() {
		return quest;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
