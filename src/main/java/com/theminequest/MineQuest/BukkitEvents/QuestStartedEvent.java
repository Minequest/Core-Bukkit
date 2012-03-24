package com.theminequest.MineQuest.BukkitEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.theminequest.MineQuest.Quest.Quest;

public class QuestStartedEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	private Quest quest;
	
	public QuestStartedEvent(Quest q){
		quest = q;
	}
	
	public Quest getQuest(){
		return quest;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
}
