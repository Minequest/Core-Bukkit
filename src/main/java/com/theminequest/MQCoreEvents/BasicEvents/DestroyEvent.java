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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.BlockBreakEvent;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Events.UserQuestEvent;
import com.theminequest.MineQuest.API.Events.QuestEvent;
import com.theminequest.MineQuest.API.Group.Group;
import com.theminequest.MineQuest.API.Group.QuestGroup;

public class DestroyEvent extends QuestEvent implements UserQuestEvent {
	
	private List<Integer> typestodestroy;
	private int totaltodestroy;
	private int currentdestroy;
	private int taskid;

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * [0]: task id to trigger
	 * [1]: block ids
	 * [2]: total # to kill
	 */
	@Override
	public void parseDetails(String[] details) {
		taskid = Integer.parseInt(details[0]);
		String[] block = details[1].split(",");
		typestodestroy = new ArrayList<Integer>();
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
		return CompleteStatus.SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.theminequest.MineQuest.Events.QEvent#blockBreakCondition(org.bukkit.event.block.BlockBreakEvent)
	 */
	@Override
	public boolean blockBreakCondition(BlockBreakEvent e) {
		QuestGroup g = Managers.getQuestGroupManager().get(getQuest());
		if (g.getMembers().contains(e.getPlayer())){
			int blockid = e.getBlock().getState().getTypeId();
			for (int t : typestodestroy){
				if (blockid==t){
					currentdestroy++;
					if (currentdestroy>=totaltodestroy)
						return true;
					else
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
		String tr = "Destroy " + (totaltodestroy-currentdestroy) + " of ";
		for (int i=0; i<typestodestroy.size(); i++){
			tr+=Material.getMaterial(typestodestroy.get(i)).toString();
			if (i<typestodestroy.size()-1)
				tr+=", ";
			else if (typestodestroy.size()==1)
				tr+="";
			else
				tr+=", and ";
		}
		tr+="!";
		return tr;
	}
	
}
