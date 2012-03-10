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
import com.theminequest.MineQuest.BukkitEvents.TaskCompleteEvent;
import com.theminequest.MineQuest.EventsAPI.QEvent;
import com.theminequest.MineQuest.Tasks.Task;
import com.theminequest.MineQuest.Team.Team;

public class Quest {
	
	private Team team;
	private String questname;
	private long questid;

	// always <ID #,OBJECT/DETAILS>
	// TreeMap guarantees key order.
	// (yes, treemap is RESOURCE intensive D:,
	// but I have to combine it with LinkedHashMap to ensure there
	// will be no duplicates)
	private TreeMap<Integer,Task> tasks;
	private TreeMap<Integer,String> events;
	private TreeMap<Integer,TargetDetails> targets;
	// quest configuration
	private String displayname;
	private boolean questRepeatable;
	private boolean spawnReset;
	/**
	 * Controls the Spawn Point for the Quest (x,y,z)
	 */
	private double[] spawnPoint;
	/**
	 * Controls the area to preserve (uneditable) (x,y,z,x,y,z)
	 */
	private double[] areaPreserve;
	private String editMessage;
	private String world;
	private boolean loadworld;

	protected Quest(long questid, String id, Team t) {
		team = t;
		questname = id;
		this.questid = questid;
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
			parseDefinition();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		// sort the tasks, events, and targets in order of id.
		// because we have absolutely 0 idea if someone would skip numbers...
		
		// load the world if necessary/move team to team leader
		if (Bukkit.getWorld(world)==null)
			Bukkit.createWorld(new WorldCreator(world));
		if (loadworld){
			try {
				world = QuestWorldManip.copyWorld(Bukkit.getWorld(world)).getName();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	/*
	 * Name:dsafasdf
Repeatable:true
Reset:true
Spawn:0:64:0
AreaPreserve:10:10:10:30:30:30
EditMessage:Eeek! No editing!
NPC:&3Echobob:0:64:0:0:0
NPCV:&4DrEeeevil:5:64:5:0:0
World:fadsf
LoadWorld::
Instance:::
QuestArea::::::
Event:3:T:StartQuest::5:
Event:2:T:NPCPropertyEvent:::
Target:132:NPCTarget:&3Echobob,
Edit::CanEdit::::
Task:0:
RepeatingTask:0:
DisallowedAbilities:Ability,Ability2,Ability3
	 */
	
	private void parseDefinition() throws FileNotFoundException {
		LinkedHashMap<Integer,Task> tasks = new LinkedHashMap<Integer,Task>();
		LinkedHashMap<Integer,String> events = new LinkedHashMap<Integer,String>();
		LinkedHashMap<Integer,TargetDetails> targets = new LinkedHashMap<Integer,TargetDetails>();
		File f = new File(MineQuest.activePlugin.getDataFolder()+File.separator+"quests"+File.separator+questname+".quest");
		Scanner filereader = new Scanner(f);
		while (filereader.hasNextLine()){
			String nextline = filereader.nextLine();
			ArrayList<String> ar = (ArrayList<String>) Arrays.asList(nextline.split(":"));
			String type = ar.get(0).toLowerCase();
			if (type.equals("name"))
				displayname = ar.get(1);				
			else if (type.equals("repeatable"))
				questRepeatable = (ar.get(1).equals("true"));
			else if (type.equals("reset"))
				spawnReset = (ar.get(1).equals("true"));
			else if (type.equals("spawn")){
				if (!ar.get(1).equals(""))
					spawnPoint[0] = Double.parseDouble(ar.get(1));
				if (!ar.get(2).equals(""))
					spawnPoint[1] = Double.parseDouble(ar.get(2));
				if (!ar.get(3).equals(""))
					spawnPoint[2] = Double.parseDouble(ar.get(3));
			} else if (type.equals("areapreserve")){
				if (!ar.get(1).equals(""))
					areaPreserve[0] = Double.parseDouble(ar.get(1));
				if (!ar.get(2).equals(""))
					areaPreserve[1] = Double.parseDouble(ar.get(2));
				if (!ar.get(3).equals(""))
					areaPreserve[2] = Double.parseDouble(ar.get(3));
				if (!ar.get(4).equals(""))
					areaPreserve[3] = Double.parseDouble(ar.get(4));
				if (!ar.get(5).equals(""))
					areaPreserve[4] = Double.parseDouble(ar.get(5));
				if (!ar.get(6).equals(""))
					areaPreserve[5] = Double.parseDouble(ar.get(6));
			} else if (type.equals("editmessage"))
				editMessage = ChatColor.GRAY+ar.get(1);
			else if (type.equals("world"))
				world = ar.get(1);
			else if (type.equals("loadworld")){
				// I say YES to instances.
				loadworld = true;
				world = ar.get(2);
				// I do NOT care about QuestArea, because
				// I simply delete the world when done.
			} else if (type.equals("event")){
				int number = Integer.parseInt(ar.get(1));
				// T = targeted event
				boolean targetedevent = false;
				if (ar.get(2).equals("T")){
					ar.remove(2);
					targetedevent = true;
				}
				String eventname = ar.get(2);
				String details = "";
				if (targetedevent)
					details+="T:";
				for (int i=3; i<ar.size(); i++){
					details+=ar.get(i);
					if (i<ar.size()-1){
						details+=":";
					}
				}
				// final result: "eventname:T:details"
				events.put(number,eventname+":"+details);
			} else if (type.equals("target")) {
				
			}
		}
		this.tasks = new TreeMap<Integer,Task>(tasks);
		this.events = new TreeMap<Integer,String>(events);
		this.targets = new TreeMap<Integer,TargetDetails>(targets);
	}
	
	/**
	 * Get all possible events
	 * @return all possible events (# association)
	 */
	public Set<Integer> getEventNums(){
		return events.keySet();
	}
	
	/**
	 * 
	 * @param eventid
	 * @return the string description of the event; null if not found.
	 */
	public String getEventDesc(int eventid){
		return events.get(eventid);
	}
	
	public void startTask(int task){
		
	}
	
	public Task getTask(int id){
		return tasks.get(id);
	}

	public long getID(){
		return questid;
	}
	
	// passed in from QuestManager
	public void onTaskCompletion(TaskCompleteEvent e){
		if (e.getQuestID()!=questid)
			return;
	}

	public List<String> getDisallowedAbilities() {
		// TODO Auto-generated method stub
		return null;
	}

}
