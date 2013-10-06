package com.theminequest.bukkit.impl.requirement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.requirement.QuestRequirement;

public class WeatherRequirement extends QuestRequirement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6471067703263659377L;
	private boolean isDownpouring;
	
	@Override
	public void parseDetails(String[] details) {
		isDownpouring = Boolean.parseBoolean(details[0]);
	}
	
	@Override
	public boolean isSatisfied(QuestDetails details, MQPlayer player) {
		Player bPlayer = Bukkit.getPlayerExact(player.getName());
		
		return bPlayer.getWorld().hasStorm() == isDownpouring;
	}
	
}
