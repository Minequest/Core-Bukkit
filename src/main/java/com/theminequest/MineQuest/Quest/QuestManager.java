package com.theminequest.MineQuest.Quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.TaskCompleteEvent;
import com.theminequest.MineQuest.Team.Team;


public class QuestManager implements Listener {

	private LinkedHashMap<Long,Quest> quests;
	private long questid;
	
	public QuestManager(){
		MineQuest.log("[Quest] Starting Manager...");
		quests = new LinkedHashMap<Long,Quest>();
		questid = 0;
	}
	
	public long startQuest(String id, Team t){
		quests.put(questid,new Quest(questid,id,t));
		long thisquestid = questid;
		questid++;
		return thisquestid;
	}

	public Quest getQuest(long currentquest) {
		if (quests.containsKey(currentquest))
			return quests.get(currentquest);
		return null;
	}
	
	@EventHandler
	public void taskCompletion(TaskCompleteEvent e){
		getQuest(e.getQuestID()).onTaskCompletion(e);
	}
	
}
