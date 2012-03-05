package com.theminequest.MineQuest.Quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.Team.Team;


public class QuestManager {

	private static LinkedHashMap<Long,Quest> quests = new LinkedHashMap<Long,Quest>();
	private static long questid = 0;
	
	public static long startQuest(String id, Team t){
		quests.put(questid,new Quest(questid,id,t));
		long thisquestid = questid;
		questid++;
		return thisquestid;
	}

	public static Quest getQuest(long currentquest) {
		if (quests.containsKey(currentquest))
			return quests.get(currentquest);
		return null;
	}
	
}
