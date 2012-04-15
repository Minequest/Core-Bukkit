/**
 * This file, CollectEvent.java, is part of MineQuest:
 * A full featured and customizable quest/mission system.
 * Copyright (C) 2012 The MineQuest Team
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 **/
package com.theminequest.MQCoreEvents.BasicEvents;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.EventsAPI.QEvent;
import com.theminequest.MineQuest.Group.Group;
import com.theminequest.MineQuest.Group.Team;

public class CollectEvent extends QEvent {
	
	private int taskid;
	private List<Integer> itemids;

	public CollectEvent(long q, int e, String details) {
		super(q, e, details);
	}

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.EventsAPI.QEvent#parseDetails(java.lang.String[])
	 * Details:
	 * [0]: task to incur upon completion
	 * [1]: itemids;
	 */
	@Override
	public void parseDetails(String[] details) {
		taskid = Integer.parseInt(details[0]);
		String[] items = details[1].split(",");
		itemids = new ArrayList<Integer>();
		for (String i : items){
			itemids.add(Integer.parseInt(i));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.EventsAPI.QEvent#conditions()
	 * Leader must have all the items.
	 */
	@Override
	public boolean conditions() {
		long gid = MineQuest.groupManager.indexOfQuest(MineQuest.questManager.getQuest(getQuestId()));
		Group g = MineQuest.groupManager.getGroup(gid);
		Player leader = g.getLeader();
		PlayerInventory i = leader.getInventory();
		for (int item : itemids){
			if (!i.contains(item))
				return false;
		}
		return true;
	}

	@Override
	public CompleteStatus action() {
		return CompleteStatus.SUCCESS;
	}

	@Override
	public Integer switchTask() {
		return taskid;
	}

}
