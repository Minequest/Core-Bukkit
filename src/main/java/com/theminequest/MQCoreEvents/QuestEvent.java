/*
 * This file, QuestEvent.java, is part of MineQuest:
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

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.EventsAPI.QEvent;

public class QuestEvent extends QEvent {

	private long milliseconds;
	private int tasktotrigger;
	private long initialmilliseconds;
	
	public QuestEvent(long q, int e, String details) {
		super(q, e, details);
	}

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.EventsAPI.QEvent#parseDetails(java.lang.String[])
	 * Basic Quest Event:
	 * [0]: delay in milliseconds
	 * [1]: task to trigger
	 */
	@Override
	public void parseDetails(String[] details) {
		milliseconds = Long.parseLong(details[0]);
		tasktotrigger = Integer.parseInt(details[1]);
		initialmilliseconds = System.currentTimeMillis();
	}

	@Override
	public boolean conditions() {
		if ((System.currentTimeMillis()-initialmilliseconds)<milliseconds)
			return false;
		return true;
	}

	@Override
	public CompleteStatus action() {
		if (!MineQuest.questManager.getQuest(this.getQuestId()).startTask(tasktotrigger))
			return CompleteStatus.FAILURE;
		return CompleteStatus.SUCCESS;
	}

}
