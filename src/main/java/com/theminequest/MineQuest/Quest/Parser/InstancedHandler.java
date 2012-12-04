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

import static com.theminequest.MineQuest.API.Quest.QuestDetails.QUEST_LOADWORLD;
import static com.theminequest.MineQuest.API.Quest.QuestDetails.QUEST_NETHERWORLD;
import static com.theminequest.MineQuest.API.Quest.QuestDetails.QUEST_WORLD;

import java.util.List;

import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;

public class InstancedHandler implements QHandler {
	
	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		q.setProperty(QUEST_LOADWORLD, true);
		q.setProperty(QUEST_WORLD, line.get(0));
		if (line.size()>1)
			q.setProperty(QUEST_NETHERWORLD, Boolean.parseBoolean(line.get(1)));
	}
	
}
