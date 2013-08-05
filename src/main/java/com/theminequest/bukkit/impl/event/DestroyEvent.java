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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.theminequest.api.CompleteStatus;
import com.theminequest.api.Managers;
import com.theminequest.api.group.Group;
import com.theminequest.api.quest.event.QuestEvent;
import com.theminequest.api.quest.event.UserQuestEvent;

public class DestroyEvent extends QuestEvent implements UserQuestEvent, Listener {
	
	public static final String DETAILS_ENTRY = "mq.events.destroyevent.%d";
	
	private List<Integer> typestodestroy;
	private int totaltodestroy;
	private int currentdestroy;
	private int taskid;
	private String entry;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * [0]: task id to trigger
	 * [1]: block ids
	 * [2]: total # to kill
	 */
	@Override
	public void setupArguments(String[] details) {
		taskid = Integer.parseInt(details[0]);
		String[] block = details[1].split(",");
		typestodestroy = new ArrayList<Integer>();
		for (String b : block)
			typestodestroy.add(Integer.parseInt(b));
		totaltodestroy = Integer.parseInt(details[2]);
		
		entry = String.format(DestroyEvent.DETAILS_ENTRY, getEventId());
		if (!getQuest().getDetails().containsProperty(entry))
			currentdestroy = 0;
		else
			currentdestroy = getQuest().getDetails().getProperty(entry);
	}
	
	@Override
	public void setUpEvent() {
		Bukkit.getPluginManager().registerEvents(this, (JavaPlugin) Managers.getPlatform().getPlatformObject());
	}

	@Override
	public boolean conditions() {
		return false;
	}
	
	@Override
	public CompleteStatus action() {
		return CompleteStatus.SUCCESS;
	}
	
	@Override
	public void cleanUpEvent() {
		if (isComplete() != CompleteStatus.IGNORE)
			getQuest().getDetails().removeProperty(entry);
		HandlerList.unregisterAll(this);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theminequest.MineQuest.Events.QEvent#blockBreakCondition(org.bukkit
	 * .event.block.BlockBreakEvent)
	 */
	@EventHandler
	public boolean blockBreakCondition(BlockBreakEvent e) {
		Group g = Managers.getGroupManager().get(getQuest());
		if (g.getMembers().contains(e.getPlayer())) {
			int blockid = e.getBlock().getState().getTypeId();
			for (int t : typestodestroy)
				if (blockid == t) {
					currentdestroy++;
					getQuest().getDetails().setProperty(entry, currentdestroy);
					if (currentdestroy >= totaltodestroy)
						return true;
					else
						return false;
				}
		}
		return false;
	}
	
	@Override
	public Integer switchTask() {
		return taskid;
	}
	
	@Override
	public String getDescription() {
		String tr = "Destroy " + (totaltodestroy - currentdestroy) + " of ";
		for (int i = 0; i < typestodestroy.size(); i++) {
			tr += Material.getMaterial(typestodestroy.get(i)).toString();
			if (i < (typestodestroy.size() - 1))
				tr += ", ";
			else if (typestodestroy.size() == 1)
				tr += "";
			else
				tr += ", and ";
		}
		tr += "!";
		return tr;
	}
	
}
