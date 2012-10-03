package com.theminequest.MQCoreRequirements;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.Requirements.QuestRequirement;

public class PermissionRequirement extends QuestRequirement {
	
	private String permission;
	
	@Override
	public void parseDetails(String[] details) {
		permission = details[0];
	}
	
	@Override
	public boolean isSatisfied(Player player) {
		return player.hasPermission(permission);
	}
	
}
