package com.theminequest.bukkit.impl.requirement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.requirement.QuestRequirement;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Requirement",
		ident = "WeatherRequirement",
		description = "Check whether the weather meets this requirement.",
		arguments = { "Stormy?" },
		typeArguments = { DocArgType.BOOL }
		)
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
