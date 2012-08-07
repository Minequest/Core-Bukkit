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
package com.theminequest.MQCoreEvents;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Events.QuestEvent;
import com.theminequest.MineQuest.API.Quest.QuestRequirement;
import com.theminequest.MineQuest.Quest.RequirementFactory;

public class RequirementMetEvent extends QuestEvent {
	
	private int taskifmet;
	private int taskifnotmet;
	private int tasktosend;
	private QuestRequirement requirement;

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.API.Events.QuestEvent#parseDetails(java.lang.String[])
	 * [0] task id if met
	 * [1] task id if not met
	 * [n] requirement details...
	 */
	@Override
	public void parseDetails(String[] details) {
		taskifmet = Integer.parseInt(details[0]);
		taskifnotmet = Integer.parseInt(details[1]);
		tasktosend = -1;
		String type = details[2];
		String req = "";
		for (int i=3; i<details.length; i++){
			req+=details[i];
			if (i<details.length-1)
				req+=":";
		}
		QuestRequirement qr = RequirementFactory.constructRequirement(type, getQuest().getDetails(), req);
		if (qr!=null)
			requirement = qr;
		else
			throw new RuntimeException("Invalid Requirement!");
	}

	@Override
	public boolean conditions() {
		return true;
	}

	@Override
	public CompleteStatus action() {
		Player leader = Managers.getQuestGroupManager().get(getQuest()).getLeader();
		if (requirement.isSatisfied(leader))
			tasktosend = taskifmet;
		else
			tasktosend = taskifnotmet;
		return CompleteStatus.SUCCESS;
	}

	@Override
	public Integer switchTask() {
		return tasktosend;
	}

}
