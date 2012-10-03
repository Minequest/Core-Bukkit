package com.theminequest.MQCoreRequirements;

import java.util.Date;
import java.util.Map;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Requirements.QuestRequirement;
import com.theminequest.MineQuest.API.Tracker.LogStatus;
import com.theminequest.MineQuest.API.Tracker.QuestStatisticUtils;

public class NotRepeatableRequirement extends QuestRequirement {
	
	@Override
	public void parseDetails(String[] details) {}
	
	@Override
	public boolean isSatisfied(Player player) {
		Map<String,Date> completed = QuestStatisticUtils.getQuests(player.getName(), LogStatus.COMPLETED);
		return completed.keySet().contains((String) getDetails().getProperty(QuestDetails.QUEST_NAME));
	}
	
}
