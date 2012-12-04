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

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.Requirements.QuestRequirement;

public class LevelRequirement extends QuestRequirement {
	
	private int level;
	private Comparison compare;
	
	private enum Comparison {
		EQUALS, LESS, LESSEQ, GREATER, GREATEREQ;
	}
	
	@Override
	public void parseDetails(String[] details) {
		level = Integer.parseInt(details[0]);
		if (details[1].equals("<"))
			compare = Comparison.LESS;
		else if (details[1].equals("<="))
			compare = Comparison.LESSEQ;
		else if (details[1].equals(">"))
			compare = Comparison.GREATER;
		else if (details[1].equals(">="))
			compare = Comparison.GREATEREQ;
		else
			compare = Comparison.EQUALS;
	}
	
	@Override
	public boolean isSatisfied(Player player) {
		int playerLvl = player.getLevel();
		switch (compare) {
		case LESS:
			return level < playerLvl;
		case LESSEQ:
			return level <= playerLvl;
		case GREATER:
			return level > playerLvl;
		case GREATEREQ:
			return level >= playerLvl;
		default:
			return level == playerLvl;
		}
	}
	
}
