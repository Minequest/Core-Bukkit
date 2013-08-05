package com.theminequest.bukkit.platform;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.theminequest.api.platform.MQEvent;

public class BukkitEvent<T extends MQEvent> extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	private T event;
	
	public BukkitEvent(T event) {
		this.event = event;
	}
	
	public String getName() {
		return event.getName();
	}
	
	public T getEvent() {
		return event;
	}
	
}
