/**
 * This file, KillEvent.java, is part of MineQuest:
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
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.EventsAPI.QEvent;
import com.theminequest.MineQuest.Group.Team;

public class KillEvent extends QEvent {
	
	private List<EntityType> typestokill;
	private int totaltokill;
	private int currentkill;
	private int taskid;

	public KillEvent(long q, int e, String details) {
		super(q, e, details);
	}

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.EventsAPI.QEvent#parseDetails(java.lang.String[])
	 * delay ms DEPRECIATED.
	 * [0]: task id to trigger
	 * [1]: entities
	 * [2]: total # to kill
	 */
	@Override
	public void parseDetails(String[] details) {
		taskid = Integer.parseInt(details[0]);
		String[] entity = details[1].split(",");
		for (String e : entity){
			typestokill.add(EntityType.fromName(e));
		}
		totaltokill = Integer.parseInt(details[2]);
		currentkill = 0;
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
	 * @see com.theminequest.MineQuest.EventsAPI.QEvent#onEntityDamageByEntityEvent(org.bukkit.event.entity.EntityDamageByEntityEvent)
	 */
	@Override
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player){
			Player p = (Player)e.getDamager();
			for (EntityType t : typestokill){
				if (e.getEntityType()==t){
					List<Player> team = MineQuest.questManager.getQuest(getQuestId()).getTeam().getPlayers();
					if (team.contains(p)){
						currentkill++;
						if (currentkill>=totaltokill)
							complete(action());
					}
				}
			}				
		}
	}

}
