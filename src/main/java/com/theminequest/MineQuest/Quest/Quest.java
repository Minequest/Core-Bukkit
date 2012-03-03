package com.theminequest.MineQuest.Quest;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import com.theminequest.MineQuest.BukkitEvents.TaskCompleteEvent;
import com.theminequest.MineQuest.Tasks.Task;
import com.theminequest.MineQuest.Team.Team;

public class Quest {
	
	private Team team;
	private String questname;
	private long questid;

	public Quest(long questid, String id, Player[] players) {
		
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
