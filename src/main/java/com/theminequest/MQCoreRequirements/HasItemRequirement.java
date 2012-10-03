package com.theminequest.MQCoreRequirements;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.theminequest.MineQuest.API.Requirements.QuestRequirement;

public class HasItemRequirement extends QuestRequirement {
	
	private int itemid;
	private short damage;
	private int qty;
	
	@Override
	public void parseDetails(String[] details) {
		itemid = Integer.parseInt(details[0]);
		damage = Short.parseShort(details[1]);
		qty = Integer.parseInt(details[2]);
	}
	
	@Override
	public boolean isSatisfied(Player player) {
		return player.getInventory().contains(new ItemStack(itemid,qty,damage));
	}
	
}
