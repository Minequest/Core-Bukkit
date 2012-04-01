/**
 * This file, QEvent.java, is part of MineQuest:
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
package com.theminequest.MineQuest.EventsAPI;

import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.BukkitEvents.EventCompleteEvent;

public abstract class QEvent{

	private long questid;
	private int eventid;
	private CompleteStatus complete;
	private int tasknumber;

	/**
	 * Initialize this QEvent with the associated Quest.<br>
	 * <b>WARNING: Classes that extend QEvent MUST NOT tamper the constructor!</b><br>
	 * Please see {@link EventManager#registerEvent(String, Class)} for reasons why.
	 * @param q Associated Quest
	 * @param e Event number
	 * @param details Details to parse
	 */
	public QEvent(long q, int e, String details){
		questid = q;
		eventid = e;
		complete = null;
		parseDetails(details.split(":"));
	}

	/**
	 * Tasks call fireEvent(). Then they wait for all events to
	 * complete, then fire off more stuff.
	 */
	public final void fireEvent(){
		System.out.println("35 REPEAT");
		MineQuest.eventManager.addEventListener(this);
		System.out.println("37 REPEAT");
		tasknumber = Bukkit.getScheduler().scheduleAsyncRepeatingTask(MineQuest.activePlugin, new Runnable(){
			@Override
			public void run() {
				if (conditions()){
					complete(action());
				}
			}
		}, 10, 10);
		System.out.println("38 REPEAT");
	}

	/**
	 * Returns the status of this event.
	 * @return Respective status, or <code>null</code> if it has
	 * not been declared yet.
	 */
	public final CompleteStatus isComplete(){
		return complete;
	}

	/**
	 * Parse the details given (: separated)
	 * @param details Parameters given
	 */
	public abstract void parseDetails(String[] details);

	/**
	 * Conditions for this event to be performed (and therefore complete)
	 * @return true if all conditions are met for this event to complete
	 */
	public abstract boolean conditions();

	/**
	 * Perform the event (and complete it, returning true if successful,
	 * false if not, and null to ignore it completely. Remember that failing
	 * an event fails the whole task, and possibly the whole mission.)
	 * @return the event action result
	 */
	public abstract CompleteStatus action();

	public final long getQuestId(){
		return questid;
	}

	public final int getEventId(){
		return eventid;
	}

	public final int getTaskId(){
		return tasknumber;
	}

	/**
	 * Notify that the event has been completed with the status given.
	 * @param actionresult Status to pass in.
	 */
	public synchronized void complete(CompleteStatus c){
		if (complete==null){
			Bukkit.getScheduler().cancelTask(tasknumber);
			MineQuest.eventManager.rmEventListener(this);
			complete = c;
			EventCompleteEvent e = new EventCompleteEvent(this,c);
			Bukkit.getPluginManager().callEvent(e);
		}
	}
	
	/**
	 * Optional method that QEvents can override if they want;
	 * by default, doesn't do anything.
	 * @param e
	 */
	public void onBlockBreak(BlockBreakEvent e){
		
	}
	
	/**
	 * Optional method that QEvents can override if they want;
	 * by default, doesn't do anything.
	 * @param e
	 */
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e){
		
	}

}
