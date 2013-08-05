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

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.theminequest.api.CompleteStatus;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.quest.event.QuestEvent;
import com.theminequest.bukkit.util.TimeUtils;

public class LockWorldTimeEvent extends QuestEvent {
	
	private long delay;
	private long time;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * Details:
	 * [0] Check delay
	 * [1] Time
	 * [2] Second Bound Time - IGNORED, DEPRECATED
	 */
	@Override
	public void setupArguments(String[] details) {
		delay = Long.parseLong(details[0]);
		time = TimeUtils.matchTime(details[1]);
	}
	
	@Override
	public boolean conditions() {
		return true;
	}
	
	@Override
	public CompleteStatus action() {
		String world = getQuest().getDetails().getProperty(QuestDetails.QUEST_WORLD);
		World questWorld = Bukkit.getWorld(world);
		TimeUtils.lock(questWorld, time, delay);
		return CompleteStatus.SUCCESS;
	}
	
	@Override
	public Integer switchTask() {
		return null;
	}
	
}
