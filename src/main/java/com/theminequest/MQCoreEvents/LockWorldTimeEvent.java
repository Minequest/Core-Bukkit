/*
 * This file, LockWorldTimeEvent.java, is part of MineQuest:
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
import org.bukkit.World;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.EventsAPI.QEvent;
import com.theminequest.MineQuest.Utils.TimeUtils;

public class LockWorldTimeEvent extends QEvent {
	
	private long delay;
	private long time;

	public LockWorldTimeEvent(long q, int e, String details) {
		super(q, e, details);
	}

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.EventsAPI.QEvent#parseDetails(java.lang.String[])
	 * Details:
	 * [0] Check delay
	 * [1] Time
	 * [2] Second Bound Time - IGNORED, DEPRECIATED
	 */
	@Override
	public void parseDetails(String[] details) {
		delay = Long.parseLong(details[0]);
		time = TimeUtils.matchTime(details[1]);
	}

	@Override
	public boolean conditions() {
		return true;
	}

	@Override
	public CompleteStatus action() {
		World questWorld = Bukkit.getWorld(MineQuest.questManager.getQuest(getQuestId()).getWorld());
		TimeUtils.lock(questWorld, time, delay);
		return CompleteStatus.SUCCESS;
	}

	@Override
	public Integer switchTask() {
		return null;
	}

}
