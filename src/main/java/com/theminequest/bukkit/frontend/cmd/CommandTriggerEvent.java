package com.theminequest.bukkit.frontend.cmd;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CommandTriggerEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	private String playerName;
	private String questName;
	private boolean tF;
	
	public CommandTriggerEvent(String playerName, String questName, boolean tF) {
		this.playerName = playerName;
		this.questName = questName;
		this.tF = tF;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getQuestName() {
		return questName;
	}
	
	public boolean getResult() {
		return tF;
	}
	
}
