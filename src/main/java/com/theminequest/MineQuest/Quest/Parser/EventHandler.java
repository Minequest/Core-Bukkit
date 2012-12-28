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
package com.theminequest.MineQuest.Quest.Parser;

import static com.theminequest.MineQuest.API.Quest.QuestDetails.QUEST_EVENTS;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;

public class EventHandler implements QHandler {

	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		Iterator<String> iter = line.iterator();
		int number = Integer.parseInt(iter.next());
		StringBuilder details = new StringBuilder();
		
		String eventname = iter.next();
		// T = targeted event
		if (eventname.equals("T")) {
			eventname = iter.next();
			details.append(eventname);
			details.append(":T");
		} else {
			details.append(eventname);
		}
		
		while (iter.hasNext()) {
			details.append(':');
			details.append(iter.next());
		}
		Map<Integer,String> events = q.getProperty(QUEST_EVENTS);
		events.put(number, details.toString());
	}

}
