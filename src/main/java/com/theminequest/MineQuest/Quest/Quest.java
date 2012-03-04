package com.theminequest.MineQuest.Quest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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

public class Quest implements Listener {
	
	private Team team;
	private String questname;
	private long questid;
	private ArrayList<Task> tasks;
	private ArrayList<QEvent> events;
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

	public Quest(long questid, String id, Team t) {
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
	 */
	
	private void parseDefinition() throws FileNotFoundException {
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
				// what is T for o.o
				if (ar.get(2).equals("T"))
					ar.remove(2);
				String eventname = ar.get(2);
				String details = "";
				for (int i=3; i<ar.size(); i++){
					details+=ar.get(i);
					if (i<ar.size()-1){
						details+=":";
					}
				}
				QEvent result = MineQuest.eventManager.getNewEvent(eventname, questid, number, details);
				if (result!=null)
					events.add(result);
			}
		}
	}

	public long getID(){
		return questid;
	}
	
	public Task getCurrentTask(){
		
	}
	
	public Task[] getTasks(){
		
	}
	
	@EventHandler
	public void onTaskCompletion(TaskCompleteEvent e){
		if (e.getQuestID()!=questid)
			return;
	}

}
