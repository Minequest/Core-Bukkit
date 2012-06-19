/**
 * This file, KillEvent.java, is part of MineQuest:
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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Events.UserQuestEvent;
import com.theminequest.MineQuest.API.Events.QuestEvent;
import com.theminequest.MineQuest.API.Group.Group;
import com.theminequest.MineQuest.API.Group.QuestGroup;
import com.theminequest.MineQuest.Group.Party;

public class KillEvent extends QuestEvent implements UserQuestEvent {

	private List<EntityType> typestokill;
	private int totaltokill;
	private int currentkill;
	private int taskid;

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * [0]: task id to trigger
	 * [1]: entities
	 * [2]: total # to kill
	 */
	@Override
	public void parseDetails(String[] details) {
		taskid = Integer.parseInt(details[0]);
		String[] entity = details[1].split(",");
		typestokill = new ArrayList<EntityType>();
		for (String e : entity){
			try {
				if (EntityType.fromName(e) != null)
					typestokill.add(EntityType.fromName(e));
				else if (EntityType.fromId(Integer.parseInt(e)) != null)
					typestokill.add(EntityType.fromId(Integer.parseInt(e)));
			} catch (Exception ignored) {}
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
		return CompleteStatus.SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.theminequest.MineQuest.Events.QEvent#entityDamageByEntityCondition(org.bukkit.event.entity.EntityDamageByEntityEvent)
	 */
	@Override
	public boolean entityDamageByEntityCondition(EntityDamageByEntityEvent e) {
		if (!(e instanceof LivingEntity))
			return false;
		LivingEntity el = (LivingEntity) e;
		if (el.getHealth()-e.getDamage()>0)
			return false;
		if (e.getDamager() instanceof Player){
			Player p = (Player)e.getDamager();
			for (EntityType t : typestokill){
				if (e.getEntityType().equals(t)){
					QuestGroup g = Managers.getQuestGroupManager().get(getQuest());
					List<Player> team = g.getMembers();
					if (team.contains(p)){
						currentkill++;
						if (currentkill>=totaltokill)
							return true;
						else
							return false;
					} else
						return false;
				}
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
		String tr = "Kill " + (totaltokill-currentkill) + " ";
		for (int i=0; i<typestokill.size(); i++){
			tr+=typestokill.get(i).getName();
			if (i<typestokill.size()-1)
				tr+="(s), ";
			else
				tr+=", and ";
		}
		tr+="(s)!";
		return tr;
	}

}
