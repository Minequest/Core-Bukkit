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
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.BukkitEvents.QuestCompleteEvent;
import com.theminequest.MineQuest.BukkitEvents.QuestStartedEvent;
import com.theminequest.MineQuest.BukkitEvents.TaskCompleteEvent;
import com.theminequest.MineQuest.Editable.AreaEdit;
import com.theminequest.MineQuest.Editable.CertainBlockEdit;
import com.theminequest.MineQuest.Editable.CoordinateEdit;
import com.theminequest.MineQuest.Editable.Edit;
import com.theminequest.MineQuest.Editable.InsideAreaEdit;
import com.theminequest.MineQuest.Editable.ItemInHandEdit;
import com.theminequest.MineQuest.Editable.OutsideAreaEdit;
import com.theminequest.MineQuest.EventsAPI.QEvent;
import com.theminequest.MineQuest.Group.Group;
import com.theminequest.MineQuest.Group.Team;
import com.theminequest.MineQuest.Target.TargetDetails;
import com.theminequest.MineQuest.Tasks.Task;
import com.theminequest.MineQuest.Utils.TimeUtils;

public class Quest {

	protected String questname;
	protected long questid;
	protected boolean started;
	protected int currenttask;

	protected CompleteStatus finished;

	// always <ID #,OBJECT/DETAILS>
	// TreeMap guarantees key order.
	// (yes, treemap is RESOURCE intensive D:,
	// but I have to combine it with LinkedHashMap to ensure there
	// will be no duplicates)
	protected LinkedHashMap<Integer, String[]> tasks;
	protected Task activeTask;
	protected LinkedHashMap<Integer, String> events;
	protected LinkedHashMap<Integer, TargetDetails> targets;
	protected LinkedHashMap<Integer, Edit> editables;
	// quest configuration
	protected String displayname;
	protected String displaydesc;
	protected String displayaccept;
	protected String displaycancel;
	protected String displayfinish;
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
	protected boolean nether;

	/*
	 * Constructor will start the quest for the user.
	 */
	protected Quest(long questid, String id) {
		questname = id;
		this.questid = questid;
		started = false;
		currenttask = -1;
		// DEFAULTS start
		displayname = questname;
		displaydesc = "This is a quest.";
		displayaccept = "You have accepted the quest.";
		displaycancel = "You have canceled the quest.";
		displayfinish = "You have finished the quest.";
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
		world = Bukkit.getWorlds().get(0).getName();
		loadworld = false;
		
		activeTask = null;

		// DEFAULTS end
		try {
			QuestParser.parseDefinition(this);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		// sort the tasks, events, and targets in order of id.
		// because we have absolutely 0 idea if someone would skip numbers...

		// load the world if necessary/move team to team leader
		if (Bukkit.getWorld(world) == null){
			WorldCreator w = new WorldCreator(world);
			if (nether)
				w = w.environment(Environment.NETHER);
			Bukkit.createWorld(w);
		}
		if (loadworld) {
			try {
				world = QuestWorldManip.copyWorld(Bukkit.getWorld(world))
						.getName();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		QuestStartedEvent event = new QuestStartedEvent(this);
		Bukkit.getPluginManager().callEvent(event);
		startTask(getFirstKey(tasks.keySet()));
	}
	
	private Integer getFirstKey(Set<Integer> s){
		int first = Integer.MAX_VALUE;
		for (int i : s){
			if (i<first)
				first = i;
		}
		return first;

	}
	
	private ArrayList<Integer> getSortedKeys(Set<Integer> s){
		ArrayList<Integer> a = new ArrayList<Integer>();
		for (Integer i : s) {
			a.add(i);
		}
		Collections.sort(a);
		return a;
	}

	/**
	 * Get all possible events
	 * 
	 * @return all possible events (# association)
	 */
	public Set<Integer> getEventNums() {
		return events.keySet();
	}

	/**
	 * Start a task of the quest.
	 * @param taskid task to start
	 * @return true if task was started successfully
	 */
	public boolean startTask(int taskid){
		System.out.println("1");
		if (taskid==-1){
			finishQuest(CompleteStatus.SUCCESS);
			return true;
		}
		System.out.println("2");
		if (!tasks.containsKey(taskid))
			return false;
		System.out.println("3");
		currenttask = taskid;
		System.out.println("4");
		String[] eventnums = tasks.get(taskid);
		System.out.println("5");
		List<Integer> eventnum = new ArrayList<Integer>();
		System.out.println("6");
		for (String e : eventnums){
			System.out.println("7 REPEAT");
			eventnum.add(Integer.parseInt(e));
		}
		System.out.println("8");
		activeTask = new Task(questid,taskid,eventnum);
		System.out.println("18");
		activeTask.start();
		System.out.println("41 SUCCESS FINISH");
		return true;
	}
	
	public Task getActiveTask(){
		return activeTask;
	}
	
	// passed in from QuestManager
	public void onTaskCompletion(TaskCompleteEvent e) {
		if (e.getQuestID() != questid)
			return;
		// TODO this is lovely and all, but tasks should trigger other tasks...
		// I'll just call the next task, and if the next task isn't available, finish the quest
		
		List<Integer> sortedkeys = getSortedKeys(tasks.keySet());
		int loc = sortedkeys.indexOf(e.getID());
		if (loc==sortedkeys.size()-1){
			finishQuest(CompleteStatus.SUCCESS);
			return;
		}
		loc++;
		startTask(loc);	
	}

	public void finishQuest(CompleteStatus c){
		finished = c;
		TimeUtils.unlock(Bukkit.getWorld(world));
		Group g = MineQuest.groupManager.getGroup(MineQuest.groupManager.indexOfQuest(this));
		QuestCompleteEvent event = new QuestCompleteEvent(questid,c,g);
		Bukkit.getPluginManager().callEvent(event);
	}
	
	public void unloadQuest() throws IOException{
		if (loadworld)
			QuestWorldManip.removeWorld(Bukkit.getWorld(world));
	}
	
	public CompleteStatus isFinished(){
		return finished;
	}
	
	/**
	 * 
	 * @param eventid
	 * @return the string description of the event; null if not found.
	 */
	public String getEventDescription(Integer eventid) {
		System.out.println("22.5 REPEAT");
		return events.get(eventid);
	}

	/**
	 * Get the "YOU CAN'T EDIT THIS PLACE" message...
	 * @return cannot edit message
	 */
	public String getEditMessage(){
		return editMessage;
	}

	/**
	 * Retrieve the current task ID.
	 * @return Current Task ID.
	 */
	public int getCurrentTaskID(){
		return currenttask;
	}

	public String[] getTaskDetails(int id) {
		return tasks.get(id);
	}

	public long getID() {
		return questid;
	}

	public String getWorld() {
		return world;
	}

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
	
	public List<String> getDisallowedAbilities() {
		// TODO not done yet
		return new ArrayList<String>();
	}
	
	public Location getSpawnLocation(){
		return new Location(Bukkit.getWorld(world),spawnPoint[0],spawnPoint[1],spawnPoint[2]);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if (!(arg0 instanceof Quest))
			return false;
		Quest q = (Quest)arg0;
		return (q.questid==this.questid);
	}

	public String getName() {
		return displayname;
	}

	public String getDescription() {
		return displaydesc;
	}

}
