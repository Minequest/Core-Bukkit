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

import java.util.List;

import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestParser.QHFields;
import com.theminequest.MineQuest.API.Quest.QuestParser.QHInfo;
import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;

@QHInfo(name = "AcceptText", description="Acceptance Text: " +
		"The message that the player will receive when he/she accepts the quest.")
@QHFields({"Message"})
public class AcceptTextHandler implements QHandler {

	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		q.setProperty(QuestDetails.QUEST_ACCEPT, line.get(0));
	}

}
