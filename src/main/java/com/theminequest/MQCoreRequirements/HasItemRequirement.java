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
package com.theminequest.MQCoreRequirements;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.theminequest.MineQuest.API.Requirements.QuestRequirement;

public class HasItemRequirement extends QuestRequirement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7767495635229931496L;
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
		return player.getInventory().contains(new ItemStack(itemid, qty, damage));
	}
	
}
