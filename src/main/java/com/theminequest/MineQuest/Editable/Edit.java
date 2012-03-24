/**
 * This file, Edit.java, is part of MineQuest:
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
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.getspout.commons.ChatColor;

import com.theminequest.MineQuest.MineQuest;

public abstract class Edit {
	
	private String details;
	private long questid;
	private int editid;
	private int taskid;
	
	public Edit(long qid, int eid, int tid, String d){
		questid = qid;
		editid = eid;
		taskid = tid;
		details = d;
	}
	
	/**
	 * Check to see if editing is allowed with the given block
	 * @param b Block that is being edited
	 * @return true if allowed
	 */
	public abstract boolean allowEdit(Block b, ItemStack i, Player p);
	
	public long getQuestId(){
		return questid;
	}
	
	public int getEditId(){
		return editid;
	}
	
	public void onBlockPlace(BlockPlaceEvent e){
		if (!allowEdit(e.getBlock(), e.getItemInHand(), e.getPlayer())){
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.YELLOW+"[!] " +MineQuest.questManager.getQuest(questid).getEditMessage());
		}
		MineQuest.questManager.getQuest(questid).startTask(taskid);
	}
	
	public void onBlockDamage(BlockDamageEvent e){
		if (!allowEdit(e.getBlock(), e.getItemInHand(), e.getPlayer())){
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.YELLOW+"[!] " +MineQuest.questManager.getQuest(questid).getEditMessage());
		}
		MineQuest.questManager.getQuest(questid).startTask(taskid);
	}

}
