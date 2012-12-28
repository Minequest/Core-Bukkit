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
package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;
import java.util.Map;

import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;
import com.theminequest.MineQuest.API.Target.TargetDetails;

public class TargetHandler implements QHandler {
	
	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		int number = Integer.parseInt(line.get(0));
		String d = "";
		for (int i = 1; i < line.size(); i++) {
			d += line.get(i);
			if (i != (line.size() - 1))
				d += ":";
		}
		Map<Integer, TargetDetails> targets = q.getProperty(QuestDetails.QUEST_TARGETS);
		targets.put(number, new TargetDetails(d));
	}
	
}
