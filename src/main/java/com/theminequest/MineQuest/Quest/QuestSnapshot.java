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
package com.theminequest.MineQuest.Quest;

import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Quest.Quest;
import com.theminequest.MineQuest.API.Quest.QuestDetails;

public class QuestSnapshot implements com.theminequest.MineQuest.API.Quest.QuestSnapshot {

	private QuestDetails details;
	private int lasttask;
	private String owner;
	
	public QuestSnapshot(Quest quest){
		details = quest.getDetails();
		if (quest.getActiveTask()!=null)
			lasttask = quest.getActiveTask().getTaskID();
		else
			lasttask = -1;
		owner = quest.getQuestOwner();
	}
	
	@Override
	public int compareTo(com.theminequest.MineQuest.API.Quest.QuestSnapshot o) {
		return details.compareTo(o.getDetails());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof QuestSnapshot))
			return false;
		QuestSnapshot s = (QuestSnapshot) obj;
		return (details.equals(s.getDetails()) && owner.equals(s.getQuestOwner()));
	}

	@Override
	public QuestDetails getDetails() {
		return details;
	}

	@Override
	public int getLastTaskID() {
		return lasttask;
	}

	@Override
	public String getQuestOwner() {
		return owner;
	}

	@Override
	public Quest recreateQuest() {
		return com.theminequest.MineQuest.Quest.Quest.newInstance(-1,getDetails(),getQuestOwner());
	}

}
