package com.theminequest.bukkit.impl.requirement;

import com.theminequest.api.Managers;
import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.requirement.QuestRequirement;
import com.theminequest.bukkit.BukkitPlatform;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Requirement",
		ident = "MoneyRequirement",
		description = "Check that players have enough money.",
		arguments = { "Money" },
		typeArguments = { DocArgType.FLOAT }
		)
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
