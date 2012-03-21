/*
 * This file, QuestAvailableEvent.java, is part of MineQuest:
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
 */
package com.theminequest.MQCoreEvents;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Backend.QuestBackend;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.EventsAPI.QEvent;
import com.theminequest.MineQuest.Team.Team;

public class QuestAvailableEvent extends QEvent {

	private long delay;
	private long time;
	private String questavailable;

	public QuestAvailableEvent(long q, int e, String details) {
		super(q, e, details);
	}

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.EventsAPI.QEvent#parseDetails(java.lang.String[])
	 * Details:
	 * [0] DELAY in MS
	 * [1] questname available
	 */
	@Override
	public void parseDetails(String[] details) {
		delay = Long.parseLong(details[0]);
		questavailable = details[1];
		time = System.currentTimeMillis();
	}

	@Override
	public boolean conditions() {
		if (System.currentTimeMillis()-time<delay)
			return false;
		return true;
	}

	@Override
	public CompleteStatus action() {
		Team t = MineQuest.questManager.getQuest(getQuestId()).getTeam();
		CompleteStatus toreturn = CompleteStatus.SUCCESS;
		for (Player p : t.getPlayers()){
			try {
				QuestBackend.giveQuestToPlayer(p,questavailable);
			} catch (IllegalArgumentException e) {
				toreturn = CompleteStatus.WARNING;
			}
		}
		return toreturn;
	}

}
