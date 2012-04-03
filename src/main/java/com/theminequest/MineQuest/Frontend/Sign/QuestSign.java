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
package com.theminequest.MineQuest.Frontend.Sign;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Backend.BackendFailedException;
import com.theminequest.MineQuest.Backend.QuestBackend;

public class QuestSign implements Listener {

	public QuestSign(){
		MineQuest.log("[QuestSign] Starting Sign Frontends...");
	}

	private boolean isQuestSign(Sign sign){
		String[] line = sign.getLines();
		if (line[1] != null && line[1].contains("[Quest]")){
			return (line[2] != null);
		}
		return false;
	}

	//Listeners For Sign interact and place. 

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event){
		Action action = event.getAction();
		if (action != Action.RIGHT_CLICK_BLOCK){
			return;
		}
		Block block = event.getClickedBlock();
		Player player = event.getPlayer();

		if (block.getState() instanceof Sign){
			Sign sign = (Sign) block.getState();
			if (isQuestSign(sign)){
				String questName = sign.getLine(2);
				try {
					QuestBackend.giveQuestToPlayer(player, questName);
					QuestBackend.acceptQuest(player, questName);
				} catch (BackendFailedException e) {
					MineQuest.log(Level.SEVERE, e.toString());
					e.printStackTrace();
					player.sendMessage(ChatColor.RED + "Error: " + e.getMessage());
				} 
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onSignChangeEvent(SignChangeEvent event) {
		if (!event.getLine(1).equalsIgnoreCase("[Quest]") && !event.getLine(1).equalsIgnoreCase("quest"))
			return;
		if (event.getLine(2).equalsIgnoreCase("")){
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "Must specify a quest!");
			event.getBlock().breakNaturally();
			return;
		}
		try {
			QuestBackend.isRepeatable(event.getLine(2));
		}catch (IllegalArgumentException e){
			MineQuest.log(Level.SEVERE,e.toString());
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "No such quest!");
			event.getBlock().breakNaturally();
			return;
		}
		// oh, prettify it ;D
		event.setLine(1,ChatColor.DARK_GREEN+"[Quest]");
	}
}