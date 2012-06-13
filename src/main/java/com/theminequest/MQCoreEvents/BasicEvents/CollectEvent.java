/**
 * This file, CollectEvent.java, is part of MineQuest:
 * A full featured and customizable quest/mission system.
 * Copyright (C) 2012 The MineQuest Party
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

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Events.UserQuestEvent;
import com.theminequest.MineQuest.API.Events.QuestEvent;
import com.theminequest.MineQuest.API.Group.Group;
import com.theminequest.MineQuest.API.Group.QuestGroup;
import com.theminequest.MineQuest.Group.Party;

public class CollectEvent extends QuestEvent implements UserQuestEvent {
	
	private int taskid;
	private List<Integer> itemids;

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
		String[] items = details[1].split(",");
		itemids = new ArrayList<Integer>();
		for (String i : items){
			itemids.add(Integer.parseInt(i));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.Events.QEvent#conditions()
	 * Leader must have all the items.
	 */
	@Override
	public boolean conditions() {
		QuestGroup g = Managers.getQuestGroupManager().get(getQuest());
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

	@Override
	public String getDescription() {
		String tr = "Collect some ";
		for (int i=0; i<itemids.size(); i++){
			tr+=Material.getMaterial(itemids.get(i)).toString();
			if (i<itemids.size()-1)
				tr+=", ";
			else
				tr+=", and ";
		}
		tr+="!";
		return tr;
	}

}
