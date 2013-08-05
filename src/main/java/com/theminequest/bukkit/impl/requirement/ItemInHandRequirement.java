package com.theminequest.bukkit.impl.requirement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.theminequest.api.platform.MQPlayer;
import com.theminequest.api.requirement.QuestRequirement;

public class ItemInHandRequirement extends QuestRequirement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1453153712933406158L;
	private int itemid;
	
	@Override
	public void parseDetails(String[] details) {
		itemid = Integer.parseInt(details[0]);
	}
	
	@Override
	public boolean isSatisfied(MQPlayer player) {
		Player p = Bukkit.getPlayerExact(player.getName());
		return p.getItemInHand().getTypeId() == itemid;
	}
	
}
