/**
 * This file, QuestSign.java, is part of MineQuest:
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
 **/
package com.theminequest.MineQuest.Frontend.QuestSign;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class QuestSign {
	
	public static boolean signCheck(Block block){
		if (block.getState() instanceof Sign == true){
				return true;
			}
		return false;
	}
	public static boolean isQuestSign(Sign sign){
		String[] line = sign.getLines();
		if (line[1] != null && (line[2].contentEquals("[Quest]"))){
			return true;
		}
		return false;
	}
	public static String questName(Sign sign){
		String[] line = sign.getLines();
		String questName = line[2].toString();
		return questName;
		
	}
}
