package com.theminequest.MineQuest.BukkitEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TaskCompleteEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	private long questid;
	private int id;
	private CompleteStatus result;
	
	public TaskCompleteEvent(long questid, int id, CompleteStatus t) {
		this.questid = questid;
		this.id = id;
		this.result = result;
	}
	
	public long getQuestID(){
		return questid;
	}
	
	public int getID(){
		return id;
	}
	
	public CompleteStatus getResult(){
		return result;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
