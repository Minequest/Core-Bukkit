package com.theminequest.MQCoreRequirements;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.Requirements.QuestRequirement;

public class WeatherRequirement extends QuestRequirement {
	
	private boolean isDownpouring;
	
	@Override
	public void parseDetails(String[] details) {
		isDownpouring = Boolean.parseBoolean(details[0]);
	}
	
	@Override
	public boolean isSatisfied(Player player) {
		return player.getWorld().hasStorm()==isDownpouring;
	}
	
}
