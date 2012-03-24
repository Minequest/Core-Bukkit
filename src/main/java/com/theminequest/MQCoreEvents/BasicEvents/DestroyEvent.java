/**
 * This file, DestroyEvent.java, is part of MineQuest:
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

import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.event.block.BlockBreakEvent;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.EventsAPI.QEvent;

public class DestroyEvent extends QEvent {
	
	private List<Integer> typestodestroy;
	private int totaltodestroy;
	private int currentdestroy;
	private int taskid;

	public DestroyEvent(long q, int e, String details) {
		super(q, e, details);
	}

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.EventsAPI.QEvent#parseDetails(java.lang.String[])
	 * delay ms DEPRECIATED
	 * [0]: task id to trigger
	 * [1]: block ids
	 * [2]: total # to kill
	 */
	@Override
	public void parseDetails(String[] details) {
		taskid = Integer.parseInt(details[0]);
		String[] block = details[1].split(",");
		for (String b : block){
			typestodestroy.add(Integer.parseInt(b));
		}
		totaltodestroy = Integer.parseInt(details[2]);
		currentdestroy = 0;
	}

	@Override
	public boolean conditions() {
		return false;
	}

	@Override
	public CompleteStatus action() {
		MineQuest.questManager.getQuest(getQuestId()).startTask(taskid);
		return CompleteStatus.SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.theminequest.MineQuest.EventsAPI.QEvent#onBlockBreak(org.bukkit.event.block.BlockBreakEvent)
	 */
	@Override
	public void onBlockBreak(BlockBreakEvent e) {
		if (MineQuest.questManager.getQuest(getQuestId()).getTeam().getPlayers().contains(e.getPlayer())){
			int blockid = e.getBlock().getType().getId();
			for (int t : typestodestroy){
				if (blockid==t){
					currentdestroy++;
					if (currentdestroy>=totaltodestroy)
						complete(action());
				}
			}
		}
	}
	
}
