/*
 * This file is part of MineQuest, The ultimate MMORPG plugin!.
 * MineQuest is licensed under GNU General Public License v3.
 * Copyright (C) 2012 The MineQuest Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.theminequest.bukkit.impl.event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.theminequest.api.CompleteStatus;
import com.theminequest.api.Managers;
import com.theminequest.api.group.Group;
import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.event.QuestEvent;
import com.theminequest.bukkit.util.ItemUtils;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Event",
		ident = "RewardEnchantedEvent",
		description = "Reward players with an enchanted item.",
		arguments = { "Next Task", "Item", "Enchanted Type #", "Enchanted Level", "Durability" },
		typeArguments = { DocArgType.INT, DocArgType.INT, DocArgType.INT, DocArgType.FLOAT }
		)
public class RewardEnchantedEvent extends QuestEvent {
	
	private int taskid;
	private Material item;
	private short itemDurability;
	private int enchantNumber;
	private int enchantLevel;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * [n] itemid,qty
	 */
	@Override
	public void setupArguments(String[] details) {
		taskid = Integer.parseInt(details[0]);
		item = ItemUtils.getMaterial(details[1]);
		enchantNumber = Integer.parseInt(details[2]);
		enchantLevel = Integer.parseInt(details[3]);
		itemDurability = Short.parseShort(details[4]);
	}
	
	@Override
	public boolean conditions() {
		return true;
	}
	
	@Override
	public CompleteStatus action() {
		Group g = Managers.getGroupManager().get(getQuest());
		Enchantment enchantment = Enchantment.getById(enchantNumber);
		if (enchantment == null)
			return CompleteStatus.FAIL;
		
		for (MQPlayer p : g.getMembers()) {
			
			Player bPlayer = Bukkit.getPlayerExact(p.getName());
			
			ItemStack itemStack = new ItemStack(item);
			itemStack.setDurability(itemDurability);
			itemStack.addUnsafeEnchantment(enchantment, enchantLevel);
			
			bPlayer.getInventory().addItem(itemStack);
		}
		return CompleteStatus.SUCCESS;
	}
	
	@Override
	public Integer switchTask() {
		return taskid;
	}
	
}