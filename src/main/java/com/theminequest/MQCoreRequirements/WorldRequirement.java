package com.theminequest.MQCoreRequirements;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.Requirements.QuestRequirement;

public class WorldRequirement extends QuestRequirement {
	
	private String world;
	
	@Override
	public void parseDetails(String[] details) {
		world = details[0];
	}
	
	@Override
	public boolean isSatisfied(Player player) {
		return player.getWorld().getName().equals(world);
	}
	
}
