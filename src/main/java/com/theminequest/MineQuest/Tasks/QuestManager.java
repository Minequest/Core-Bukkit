package com.theminequest.MineQuest.Tasks;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.Quest.Quest;

public class QuestManager {

	private static HashMap<Long,Quest> quests = new HashMap<Long,Quest>();
	private static long questid = 0;
	
	public static long startQuest(String id, Player[] players){
		quests.put(questid,new Quest(questid,id,players));
		questid++;
		return questid;
	}
	
}
