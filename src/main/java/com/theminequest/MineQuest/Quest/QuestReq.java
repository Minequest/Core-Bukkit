package com.theminequest.MineQuest.Quest;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Quest.QuestRequirement;
import com.theminequest.MineQuest.API.Tracker.QuestStatistic;
import com.theminequest.MineQuest.API.Quest.QuestDetails;

public class QuestReq implements QuestRequirement {
	
	private Type type;
	private String details;
	private QuestDetails quest;
	
	public QuestReq(Type type, QuestDetails quest, String details){
		this.type = type;
		this.details = details;
		this.quest = quest;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public String getDetails() {
		return details;
	}
	
	@Override
	public QuestDetails getQuest() {
		return quest;
	}

	@Override
	public boolean isSatisfied(Player player) {
		switch(type){
		case NEVERDONE:
			QuestStatistic stat = Managers.getStatisticManager().getStatistic(player.getName(),QuestStatistic.class);
			for (String q : stat.getCompletedQuests())
				if (q.equals(quest.getProperty(QuestDetails.QUEST_NAME)))
					return false;
			return true;
		default:
			return false;
		}
	}



}
