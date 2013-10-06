package com.theminequest.bukkit.impl.requirement;

import com.theminequest.api.Managers;
import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.requirement.QuestRequirement;
import com.theminequest.bukkit.BukkitPlatform;

public class MoneyRequirement extends QuestRequirement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 657947257948937593L;
	private double money;
	
	@Override
	public void parseDetails(String[] details) {
		money = Double.parseDouble(details[0]);
	}
	
	@Override
	public boolean isSatisfied(QuestDetails details, MQPlayer player) {
		BukkitPlatform platform = (BukkitPlatform) Managers.getPlatform().getPlatformObject();
		
		if (platform.getEconomy() == null)
			return false;
		
		return platform.getEconomy().has(player.getName(), money);
	}
	
}
