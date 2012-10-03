package com.theminequest.MQCoreRequirements;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Group.QuestGroup;
import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Requirements.QuestRequirement;

public class GroupSizeRequirement extends QuestRequirement {
	
	private int size;
	
	@Override
	public void parseDetails(String[] details) {
		size = Integer.parseInt(details[0]);
	}
	
	@Override
	public boolean isSatisfied(Player player) {
		Boolean isInstanced = getDetails().getProperty(QuestDetails.QUEST_LOADWORLD);
		if (!isInstanced)
			return true;
		
		QuestGroup gsg = Managers.getQuestGroupManager().get(player);
		return (gsg.getMembers().size()<=size);
	}
	
}
