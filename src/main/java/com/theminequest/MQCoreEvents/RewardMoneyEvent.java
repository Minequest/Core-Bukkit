/*
 * This file, RewardMoneyEvent.java, is part of MineQuest:
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
 */
package com.theminequest.MQCoreEvents;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Events.QuestEvent;
import com.theminequest.MineQuest.API.Group.Group;
import com.theminequest.MineQuest.API.Group.QuestGroup;
import com.theminequest.MineQuest.Group.Party;

public class RewardMoneyEvent extends QuestEvent {
	
	private double money;

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * Details:
	 * [0] Amount of money to give
	 */
	@Override
	public void parseDetails(String[] details) {
		money = Double.parseDouble(details[0]);
	}

	@Override
	public boolean conditions() {
		return true;
	}

	@Override
	public CompleteStatus action() {
		QuestGroup g = Managers.getQuestGroupManager().get(getQuest());
		if (MineQuest.economy==null)
			return CompleteStatus.WARNING;
		for (Player p : g.getMembers())
			MineQuest.economy.depositPlayer(p.getName(), money);
		return CompleteStatus.SUCCESS;
	}

	@Override
	public Integer switchTask() {
		return null;
	}

}
