/**
 * This file, EventCompleteEvent.java, is part of MineQuest:
 * A full featured and customizable quest/mission system.
 * Copyright (C) 2012 The MineQuest Team
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 **/
package com.theminequest.MineQuest.BukkitEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.theminequest.MineQuest.CompleteStatus;
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
