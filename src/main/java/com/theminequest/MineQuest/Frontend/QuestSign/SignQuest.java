/**
 * This file, SignInteractListener.java, is part of MineQuest:
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

import java.io.File;
import java.io.FileNotFoundException;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Backend.QuestBackend;

public class SignQuest implements Listener {
	
	public static boolean signCheck(Block block){
		if (block.getState() instanceof Sign){
				return true;
			}
		return false;
	}
	public static boolean isQuestSign(Sign sign){
		String[] line = sign.getLines();
		if (line[1] != null && (line[1].contentEquals("[Quest]"))){
			if(line[2] != null){
				return true;
			}
		}
		return false;
	}
	public static String questName(Sign sign){
		String[] line = sign.getLines();
		String questName = line[2].toString();
		return questName;
	}
	
	
	//Listeners For Sign interact and place. 
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onPlayerInteract(PlayerInteractEvent event){
		Action action = event.getAction();
		if (action != Action.RIGHT_CLICK_BLOCK){
			return;
		}
		Block block = event.getClickedBlock();
		Player player = event.getPlayer();
		
		if (signCheck(block) == true){
			Sign sign = (Sign) block.getState();
			if (isQuestSign(sign) == true){
				String questName = questName(sign);
				try {
					if (checkQuest(questName) == true){
						QuestBackend.giveQuestToPlayer(player, questName);
					}
				} catch (FileNotFoundException e) {
					MineQuest.log("Quest File not found.");
					e.printStackTrace();
				} 
			}
		}
	}
	public static boolean checkQuest(String questName) throws FileNotFoundException{
		File f = new File(MineQuest.activePlugin.getDataFolder()+File.separator+"quests"+File.separator+questName+".quest");
		if (f.exists() != true){
			return true;
		}
		else{
			return false;
		}
	}
	@EventHandler
	public static void onBlockPlace(BlockPlaceEvent event) {
		Block block = event.getBlockAgainst();
	    if (signCheck(block) && isQuestSign((Sign) block.getState())) {
	        event.setCancelled(true);
	        return;
	    }
	    
	}
}
