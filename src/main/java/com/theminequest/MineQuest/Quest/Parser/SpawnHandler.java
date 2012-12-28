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

import static com.theminequest.MineQuest.API.Quest.QuestDetails.QUEST_SPAWNPOINT;

import java.util.List;

import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;

public class SpawnHandler implements QHandler {

	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		double[] spawnPoint = q.getProperty(QUEST_SPAWNPOINT);
		if (!line.get(0).equals(""))
			spawnPoint[0] = Double.parseDouble(line.get(0));
		if (!line.get(1).equals(""))
			spawnPoint[1] = Double.parseDouble(line.get(1));
		if (!line.get(2).equals(""))
			spawnPoint[2] = Double.parseDouble(line.get(2));
	}

}
