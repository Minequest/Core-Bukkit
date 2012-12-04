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
package com.theminequest.MQCoreRequirements;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.Requirements.QuestRequirement;

public class RealDateRequirement extends QuestRequirement{
	
	private Date before;
	private Date after;
	
	@Override
	public void parseDetails(String[] details) {
		try {
			before = DateFormat.getInstance().parse(details[0]);
			after = DateFormat.getInstance().parse(details[1]);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public boolean isSatisfied(Player player) {
		Date current = Calendar.getInstance().getTime();
		return current.after(before) && current.before(after);
	}
	
}
