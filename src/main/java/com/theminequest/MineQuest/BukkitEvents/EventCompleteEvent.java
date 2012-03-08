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
	private CompleteStatus status;
	
	public EventCompleteEvent(QEvent e, CompleteStatus c){
		event = e;
		status = c;
	}
	
	public QEvent getEvent(){
		return event;
	}
	
	public CompleteStatus getCompleteStatus(){
		return status;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
