package com.theminequest.MQCoreRequirements;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.Requirements.QuestRequirement;
import com.theminequest.MineQuest.API.Tracker.LogStatus;
import com.theminequest.MineQuest.API.Tracker.QuestStatisticUtils;

public class QuestCompletedRequirement extends QuestRequirement {

	private String questToCheck;
	
	@Override
	public void parseDetails(String[] details) {
		questToCheck = details[0];
	}

	@Override
	public boolean isSatisfied(Player player) {
		LogStatus ls = QuestStatisticUtils.hasQuest(player.getName(), questToCheck);
		return ls == LogStatus.COMPLETED;
	}
	
}
