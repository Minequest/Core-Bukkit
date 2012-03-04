package com.theminequest.MineQuest.Quest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.TaskCompleteEvent;
import com.theminequest.MineQuest.EventsAPI.QEvent;
import com.theminequest.MineQuest.Tasks.Task;
import com.theminequest.MineQuest.Team.Team;

public class Quest {
	
	private Team team;
	private String questname;
	private long questid;
	private ArrayList<Task> tasks;
	private ArrayList<QEvent> events;

	public Quest(long questid, String id, Team t) {
		team = t;
		questname = id;
		this.questid = questid;
		try {
			parseDefinition();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void parseDefinition() throws FileNotFoundException {
		File f = new File(MineQuest.activePlugin.getDataFolder()+File.separator+"quests"+File.separator+questname+".quest");
		Scanner filereader = new Scanner(f);
		while (filereader.hasNextLine()){
			String nextline = filereader.nextLine();
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
