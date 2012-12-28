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

import com.theminequest.MineQuest.API.Edit.CertainBlockEdit;
import com.theminequest.MineQuest.API.Edit.CoordinateEdit;
import com.theminequest.MineQuest.API.Edit.Edit;
import com.theminequest.MineQuest.API.Edit.InsideAreaEdit;
import com.theminequest.MineQuest.API.Edit.ItemInHandEdit;
import com.theminequest.MineQuest.API.Edit.OutsideAreaEdit;
import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;

public class EditHandler implements QHandler {
	
	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		int number = Integer.parseInt(line.get(0));
		String edittype = line.get(1);
		String d = "";
		for (int i = 2; i < line.size(); i++) {
			d += line.get(i);
			if (i != (line.size() - 1))
				d += ":";
		}
		Edit e;
		if (edittype.equalsIgnoreCase("CanEdit"))
			e = new CoordinateEdit(number, Integer.parseInt(d.split(":")[3]), d);
		else if (edittype.equalsIgnoreCase("CanEditArea"))
			e = new InsideAreaEdit(number, Integer.parseInt(d.split(":")[6]), d);
		else if (edittype.equalsIgnoreCase("CanEditOutsideArea"))
			e = new OutsideAreaEdit(number, Integer.parseInt(d.split(":")[6]), d);
		else {
			int taskid = Integer.parseInt(line.get(2));
			d = "";
			for (int i = 3; i < line.size(); i++) {
				d += line.get(i);
				if (i != (line.size() - 1))
					d += ":";
			}
			if (edittype.equalsIgnoreCase("CanEditTypesInHand"))
				e = new ItemInHandEdit(number, taskid, d);
			else
				e = new CertainBlockEdit(number, taskid, d);
		}
		Map<Integer, Edit> ed = q.getProperty(QuestDetails.QUEST_EDITS);
		ed.put(number, e);
	}
	
}
