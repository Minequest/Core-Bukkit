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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.theminequest.MQCoreEvents.BasicEvents;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Events.QuestEvent;
import com.theminequest.MineQuest.API.Events.UserQuestEvent;
import com.theminequest.MineQuest.API.Utils.InventoryUtils;

public class NRCollectEvent extends QuestEvent implements UserQuestEvent {
	
	private int taskid;
	private Map<Material, Integer> itemMap;
	private Future<Boolean> futureTask;

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * Details:
	 * [0]: task to incur upon completion
	 * [1]: itemids;
	 * [2]: totaltocollect
	 */
	@Override
	public void parseDetails(String[] details) {
		taskid = Integer.parseInt(details[0]);
		itemMap = Collections.synchronizedMap(new LinkedHashMap<Material, Integer>());
		String[] items = details[1].split(",");
		String[] amounts = details[2].split(",");
		
		for (int i = 0; i < items.length; i++) {
			String item = items[i];
			Integer amount = null;
			try {
				if (amounts.length == 1) {
						amount = Integer.valueOf(amounts[0]);
				} else if (i < amounts.length) {
					amount = Integer.valueOf(amounts[i]);
				}
			} catch (NumberFormatException e) {}
			
			if (amount == null) {
				Managers.log(Level.SEVERE, "[Event] In CollectEvent, could not determine amount of items to collect for "+item);
				continue;
			}
			
			Material m = Material.matchMaterial(item.toUpperCase());
			if (m == null) {
				try {
					m = Material.getMaterial(Integer.valueOf(item));
				} catch (NumberFormatException e) {}
			}
			
			if (m == null) {
				Managers.log(Level.SEVERE, "[Event] In CollectEvent, could not determine material of "+item);
				continue;
			}
			itemMap.put(m, amount);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.Events.QEvent#conditions()
	 */
	@Override
	public boolean conditions() {
		if (futureTask != null) {
			if (futureTask.isDone()) {
				Boolean ret = null;
				try {
					ret = futureTask.get();
				} catch (Throwable t) {
					t.printStackTrace();
				}
				futureTask = null;
				if (ret != null && ret.booleanValue())
					return true;
			}
			return false;
		}
		
		Callable<Boolean> c = new Callable<Boolean>() {
			public Boolean call() {
				Player p = Bukkit.getServer().getPlayerExact(getQuest().getQuestOwner());
				if (p == null)
					return false;
				
				PlayerInventory i = p.getInventory();
				for (Map.Entry<Material, Integer> entry : itemMap.entrySet()) {
					Material m = entry.getKey();
					int amount = entry.getValue();
					if (!InventoryUtils.inventoryContains(i, m, 0, amount))
						return false;
				}
				
				return true;
			}
		};
		
		futureTask = Bukkit.getScheduler().callSyncMethod(Managers.getActivePlugin(), c);
		
		return false;
	}

	@Override
	public CompleteStatus action() {
		return CompleteStatus.SUCCESS;
	}

	@Override
	public Integer switchTask() {
		return taskid;
	}

	@Override
	public String getDescription() {
		StringBuilder builder = new StringBuilder();
		builder.append("Collect ");
		boolean first = true;
		int i = 0;
		for (Map.Entry<Material, Integer> entry : itemMap.entrySet()) {
			i++;
			if (first) {
				first = false;
			} else {
				builder.append(", ");
				
				if (i == itemMap.size())
					builder.append("and ");
			}
			
			builder.append(entry.getValue().toString()).append(" ").append(entry.getKey().toString());
		}
		builder.append("!");
		return builder.toString();
	}

}
