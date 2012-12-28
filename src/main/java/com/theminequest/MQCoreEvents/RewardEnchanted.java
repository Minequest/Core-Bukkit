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
package com.theminequest.MQCoreEvents;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Events.QuestEvent;
import com.theminequest.MineQuest.API.Group.QuestGroup;
import com.theminequest.MineQuest.API.Utils.ItemUtils;

public class RewardEnchanted extends QuestEvent {
	
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
	public void parseDetails(String[] details) {
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
		QuestGroup g = Managers.getQuestGroupManager().get(getQuest());
		Enchantment enchantment = Enchantment.getById(enchantNumber);
		if (enchantment == null)
			return CompleteStatus.FAILURE;
		
		for (Player p : g.getMembers()) {
			
			ItemStack itemStack = new ItemStack(item);
			itemStack.setDurability(itemDurability);
			itemStack.addEnchantment(enchantment, enchantLevel);
			
			p.getInventory().addItem(itemStack);
		}
		return CompleteStatus.SUCCESS;
	}
	
	@Override
	public Integer switchTask() {
		return taskid;
	}
	
}