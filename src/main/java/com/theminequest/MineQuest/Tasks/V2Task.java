/*
 * This file is part of MineQuest, The ultimate MMORPG plugin!.
 * MineQuest is licensed under GNU General Public License v3.
 * Copyright (C) 2012 The MineQuest Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.theminequest.MineQuest.Tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.BukkitEvents.TaskCompleteEvent;
import com.theminequest.MineQuest.API.Events.QuestEvent;
import com.theminequest.MineQuest.API.Quest.Quest;
import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestUtils;
import com.theminequest.MineQuest.API.Task.QuestTask;

/**
 * V2Task operates according to the 1.2.5 MineQuest System,
 * which each set of events run linked to a task.
 * Whenever a new task is toggled, all events linked to the old task
 * are canceled, and the task itself canceled as well.
 * 
 */
public class V2Task implements QuestTask {
	
	/**
	 * 
	 */
	private boolean started;
	private volatile CompleteStatus complete;
	private Quest quest;
	private int taskid;
	private LinkedHashMap<Integer, QuestEvent> collection;
	
	/**
	 * Task for a Quest.
	 * 
	 * @param questid
	 *            Associated Quest
	 * @param taskid
	 *            Task ID
	 * @param events
	 *            Event numbers that must be completed
	 */
	public V2Task(Quest quest, int taskid, List<Integer> events) {
		started = false;
		complete = null;
		this.quest = quest;
		this.taskid = taskid;
		collection = new LinkedHashMap<Integer, QuestEvent>();
		for (int e : events)
			collection.put(e, null);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theminequest.MineQuest.Tasks.QuestTask#start()
	 */
	@Override
	public void start() {
		if (started)
			return;
		started = true;
		List<Integer> list = new ArrayList<Integer>(collection.keySet());
		for (Integer event : list) {
			String d = QuestUtils.getEvent(quest, event);
			if (d == null) {
				Managers.log(Level.WARNING, "[Task] Missing event number " + event + " in V2Task " + taskid + " for quest " + quest.getDetails().getProperty(QuestDetails.QUEST_NAME) + "; Ignoring.");
				collection.remove(event);
				continue;
			}
			String[] eventdetails = d.split(":");
			String recombined = "";
			for (int r = 1; r < eventdetails.length; r++) {
				recombined += eventdetails[r];
				if (r != (eventdetails.length - 1))
					;
				recombined += ":";
			}
			QuestEvent e = Managers.getEventManager().constructEvent(eventdetails[0], quest, event, recombined);
			if (e != null)
				collection.put(event, e);
			else {
				Managers.log(Level.WARNING, "[Task] Missing event " + eventdetails[0] + "; Ignoring.");
				collection.remove(event);
			}
		}
		
		Managers.getEventManager().registerEventListeners(Collections.unmodifiableCollection(collection.values()));
		for (QuestEvent e : collection.values())
			e.fireEvent();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theminequest.MineQuest.Tasks.QuestTask#cancelTask()
	 */
	@Override
	public void cancelTask() {
		completeTask(CompleteStatus.CANCELED);
	}
	
	@Override
	public void checkTasks() {
		for (QuestEvent e : collection.values())
			if (e.isComplete() == null)
				return;
		completeTask(CompleteStatus.SUCCESS);
	}
	
	@Override
	public void completeTask(CompleteStatus status) {
		if ((complete != null) || !started)
			return;
		complete = status;
		
		CompleteStatus eventCStatus = (status == CompleteStatus.IGNORE) ? CompleteStatus.IGNORE : CompleteStatus.CANCELED;
		
		for (QuestEvent e : collection.values())
			e.complete(eventCStatus);
		
		TaskCompleteEvent e = new TaskCompleteEvent(quest, taskid, status);
		Bukkit.getPluginManager().callEvent(e);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theminequest.MineQuest.Tasks.QuestTask#isComplete()
	 */
	@Override
	public CompleteStatus isComplete() {
		return complete;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theminequest.MineQuest.Tasks.QuestTask#getQuestID()
	 */
	@Override
	public Quest getQuest() {
		return quest;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theminequest.MineQuest.Tasks.QuestTask#getTaskID()
	 */
	@Override
	public int getTaskID() {
		return taskid;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theminequest.MineQuest.Tasks.QuestTask#getEvents()
	 */
	@Override
	public Collection<QuestEvent> getEvents() {
		return collection.values();
	}
	
}
