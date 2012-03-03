package com.theminequest.MineQuest.BukkitEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.theminequest.MineQuest.EventsAPI.QEvent;

public class EventCompleteEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	private QEvent event;
	
	public EventCompleteEvent(QEvent e){
		event = e;
	}
	
	public QEvent getEvent(){
		return event;
	}

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}

}
