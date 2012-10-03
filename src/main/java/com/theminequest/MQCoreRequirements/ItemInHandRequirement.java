package com.theminequest.MQCoreRequirements;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.Requirements.QuestRequirement;

public class ItemInHandRequirement extends QuestRequirement {

	private int itemid;
	
	@Override
	public void parseDetails(String[] details) {
		itemid = Integer.parseInt(details[0]);
	}

	@Override
	public boolean isSatisfied(Player player) {
		return player.getItemInHand().getTypeId()==itemid;
	}
	
}
