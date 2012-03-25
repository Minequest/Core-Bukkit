/**
 * This file, Task.java, is part of MineQuest:
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
package com.theminequest.MineQuest.Tasks;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Bukkit;
import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.BukkitEvents.TaskCompleteEvent;
import com.theminequest.MineQuest.EventsAPI.QEvent;
import com.theminequest.MineQuest.Quest.QuestManager;

public class Task {
	
	private boolean started;
	private boolean complete;
	private long questid;
	private int taskid;
	private LinkedHashMap<Integer,Boolean> events;
	private List<QEvent> objects;
	
	/**
	 * Task for a Quest.
	 * @param questid Associated Quest
	 * @param taskid Task ID
	 * @param events Event numbers that must be completed
	 */
	public Task(long questid, int taskid, List<Integer> events){
		started = false;
		complete = false;
		this.questid = questid;
		this.taskid = taskid;
		this.events = new LinkedHashMap<Integer,Boolean>();
		for (int e : events){
			this.events.put(e, false);
		}
		this.objects = new ArrayList<QEvent>();
	}
	
	public synchronized void start(){
		if (started)
			return;
		started = true;
		for (int eventid : events.keySet()){
			String eventdesc = MineQuest.questManager.getQuest(questid).getEventDesc(eventid);
			String[] details = eventdesc.split(":");
			String eventname = details[0];
			String passind = "";
			for (int i=1; i<details.length; i++)
				passind+=details[i];
			QEvent e = MineQuest.eventManager.getNewEvent(eventname, questid, eventid, passind);
			if (e==null)
				// invalid event.
				events.remove(eventid);
			e.fireEvent();
			objects.add(e);
		}
	}
	
	public synchronized void cancelTask(){
		if (complete || !started)
			return;
		complete = true;
		for (QEvent e : objects){
			e.complete(CompleteStatus.CANCELED);
		}
		TaskCompleteEvent e = new TaskCompleteEvent(questid, taskid, CompleteStatus.CANCELED);
		Bukkit.getPluginManager().callEvent(e);
	}
	
	public synchronized void finishEvent(int eventid, CompleteStatus completeStatus ){
		if (!complete && started && events.containsKey(eventid) && !events.get(eventid)){
			events.put(eventid, true);
			if (completeStatus==CompleteStatus.FAILURE){
				for (QEvent event : objects)
					event.complete(CompleteStatus.CANCELED);
				complete = true;
				TaskCompleteEvent e = new TaskCompleteEvent(questid,taskid,CompleteStatus.FAILURE);
				Bukkit.getPluginManager().callEvent(e);
			}else
				checkCompletion();
		}
	}
	
	private synchronized void checkCompletion() {
		for (Integer eventid : events.keySet()){
			if (!events.get(eventid))
				return;
		}
		complete = true;
		TaskCompleteEvent e = new TaskCompleteEvent(questid, taskid, CompleteStatus.SUCCESS);
		Bukkit.getPluginManager().callEvent(e);
	}

	public boolean isComplete(){
		return complete;
	}
	
	public long getQuestID(){
		return questid;
	}
	
	public int getTaskID(){
		return taskid;
	}
	
	public List<QEvent> getEventsRunning(){
		return objects;
	}

}
