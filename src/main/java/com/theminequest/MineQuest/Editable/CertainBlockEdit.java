/**
 * This file, CertainBlockEdit.java, is part of MineQuest:
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
package com.theminequest.MineQuest.Editable;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CertainBlockEdit extends Edit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2586999166842874789L;
	private String[] blockIDs;
	
	public CertainBlockEdit(int eid, int tid, String d) {
		super(eid, tid, d);
	}

	@Override
	public boolean allowEdit(Block b, ItemStack i, Player p) {
		for (String s : blockIDs){
			if (Integer.parseInt(s)==b.getTypeId())
				return true;
		}
		return false;
	}

	@Override
	public void parseDetails(String d) {
		blockIDs = d.split(",");
	}

}
