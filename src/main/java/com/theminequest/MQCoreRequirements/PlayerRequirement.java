package com.theminequest.MQCoreRequirements;

import java.util.Arrays;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.Requirements.QuestRequirement;

public class PlayerRequirement extends QuestRequirement {
	
	private String[] players;
	
	@Override
	public void parseDetails(String[] details) {
		players = details;
	}
	
	@Override
	public boolean isSatisfied(Player player) {
		Arrays.sort(players);
		return Arrays.binarySearch(players, player.getName()) >= 0;
	}
	
}
