package com.theminequest.bukkit.impl.requirement;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.requirement.QuestRequirement;
import com.theminequest.bukkit.util.ItemUtils;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Requirement",
		ident = "ItemInHandRequirement",
		description = "Check that the player has an item in their hand.",
		arguments = { "Item" },
		typeArguments = { DocArgType.STRING }
		)
public class ItemInHandRequirement extends QuestRequirement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1453153712933406158L;
	private Material item;
	
	@Override
	public void parseDetails(String[] details) {
		item = ItemUtils.getMaterial(details[0]);
	}
	
	@Override
	public boolean isSatisfied(QuestDetails details, MQPlayer player) {
		Player p = Bukkit.getPlayerExact(player.getName());
		return p.getItemInHand().getType() == item;
	}
	
}
