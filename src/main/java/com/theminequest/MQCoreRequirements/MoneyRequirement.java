package com.theminequest.MQCoreRequirements;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.API.Requirements.QuestRequirement;

public class MoneyRequirement extends QuestRequirement {
	
	private double money;
	
	@Override
	public void parseDetails(String[] details) {
		money = Double.parseDouble(details[0]);
	}
	
	@Override
	public boolean isSatisfied(Player player) {
		return MineQuest.economy.has(player.getName(), money);
	}
	
}
