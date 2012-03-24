/**
 * This file, Quest.java, is part of MineQuest:
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
package com.theminequest.MineQuest.Quest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.BukkitEvents.QuestCompleteEvent;
import com.theminequest.MineQuest.BukkitEvents.TaskCompleteEvent;
import com.theminequest.MineQuest.Editable.AreaEdit;
import com.theminequest.MineQuest.Editable.CertainBlockEdit;
import com.theminequest.MineQuest.Editable.CoordinateEdit;
import com.theminequest.MineQuest.Editable.Edit;
import com.theminequest.MineQuest.Editable.InsideAreaEdit;
import com.theminequest.MineQuest.Editable.ItemInHandEdit;
import com.theminequest.MineQuest.Editable.OutsideAreaEdit;
import com.theminequest.MineQuest.EventsAPI.QEvent;
import com.theminequest.MineQuest.Target.TargetDetails;
import com.theminequest.MineQuest.Tasks.Task;
import com.theminequest.MineQuest.Team.Team;

public class Quest {

	protected Team team;
	protected String questname;
	protected long questid;
	protected boolean started;
	protected int currenttask;

	// always <ID #,OBJECT/DETAILS>
	// TreeMap guarantees key order.
	// (yes, treemap is RESOURCE intensive D:,
	// but I have to combine it with LinkedHashMap to ensure there
	// will be no duplicates)
	protected TreeMap<Integer, Task> tasks;
	protected TreeMap<Integer, String> events;
	protected TreeMap<Integer, TargetDetails> targets;
	protected TreeMap<Integer, Edit> editables;
	// quest configuration
	protected String displayname;
	protected boolean questRepeatable;
	protected boolean spawnReset;
	/**
	 * Controls the Spawn Point for the Quest (x,y,z)
	 */
	protected double[] spawnPoint;
	/**
	 * Controls the area to preserve (uneditable) (x,y,z,x,y,z)
	 */
	protected double[] areaPreserve;
	protected String editMessage;
	protected String world;
	protected boolean loadworld;

	protected Quest(long questid, String id, Team t) {
		team = t;
		questname = id;
		this.questid = questid;
		started = false;
		currenttask = -1;
		// DEFAULTS start
		displayname = questname;
		questRepeatable = false;
		spawnReset = true;

		spawnPoint = new double[3];
		spawnPoint[0] = 0;
		spawnPoint[1] = 64;
		spawnPoint[2] = 0;

		areaPreserve = new double[6];
		areaPreserve[0] = 0;
		areaPreserve[1] = 64;
		areaPreserve[2] = 0;
		areaPreserve[3] = 0;
		areaPreserve[4] = 64;
		areaPreserve[5] = 0;

		editMessage = ChatColor.GRAY + "You cannot edit inside a quest.";
		world = t.getLeader().getWorld().getName();
		loadworld = false;

		// DEFAULTS end
		try {
			QuestParser.parseDefinition(this);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		// sort the tasks, events, and targets in order of id.
		// because we have absolutely 0 idea if someone would skip numbers...

		// load the world if necessary/move team to team leader
		if (Bukkit.getWorld(world) == null)
			Bukkit.createWorld(new WorldCreator(world));
		if (loadworld) {
			try {
				world = QuestWorldManip.copyWorld(Bukkit.getWorld(world))
						.getName();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Get all possible events
	 * 
	 * @return all possible events (# association)
	 */
	public Set<Integer> getEventNums() {
		return events.keySet();
	}

	public void finishQuest(CompleteStatus c){
		// TODO STUB
		QuestCompleteEvent event = new QuestCompleteEvent(questid,c,team);
		Bukkit.getPluginManager().callEvent(event);
	}
	
	/**
	 * 
	 * @param eventid
	 * @return the string description of the event; null if not found.
	 */
	public String getEventDesc(int eventid) {
		return events.get(eventid);
	}

	/**
	 * Start the Quest. Launches each task in an asynchronous thread.
	 */
	public void startQuest(){
		// TODO
		startTask(tasks.firstKey());
	}

	/**
	 * Get the "YOU CAN'T EDIT THIS PLACE" message...
	 * @return cannot edit message
	 */
	public String getEditMessage(){
		return editMessage;
	}

	/**
	 * Start a task of the quest.
	 * @param taskid task to start
	 * @return true if task was started successfully
	 */
	public boolean startTask(int taskid){
		if (taskid==-1){
			finishQuest(CompleteStatus.SUCCESS);
			return true;
		}
		if (!tasks.containsKey(taskid))
			return false;
		currenttask = taskid;
		tasks.get(taskid).start();
		return true;
	}

	/**
	 * Retrieve the current task ID.
	 * @return Current Task ID, or <code>-1</code> if none is running.
	 */
	public int getCurrentTaskID(){
		return currenttask;
	}

	public Task getTask(int id) {
		return tasks.get(id);
	}

	public long getID() {
		return questid;
	}

	public String getWorld() {
		return world;
	}
	
	// TODO on finishing quest, unlock Time in TimeUtils if necessary.

	/**
	 * Retrieve the target specification.
	 * 
	 * @param id
	 *            target ID
	 * @return specification, or <code>null</code> if there is no such target
	 *         id.
	 */
	public TargetDetails getTarget(int id) {
		return targets.get(id);
	}

	public Team getTeam() {
		return team;
	}

	// passed in from QuestManager
	public void onTaskCompletion(TaskCompleteEvent e) {
		if (e.getQuestID() != questid)
			return;
		// TODO
	}

	public List<String> getDisallowedAbilities() {
		// TODO Auto-generated method stub
		return null;
	}

}
