/*
 * This file, MessageEvent.java, is part of MineQuest:
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
 */
package com.theminequest.MQCoreEvents;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.EventsAPI.QEvent;
import com.theminequest.MineQuest.Team.Team;
import com.theminequest.MineQuest.Utils.ChatUtils;

public class MessageEvent extends QEvent {
	
	private String message;

	public MessageEvent(long q, int e, String details) {
		super(q, e, details);
	}

	@Override
	public void parseDetails(String[] details) {
		message = "";
		for (int i=0; i<details.length; i++){
			message+=details[i];
			if (i!=details.length-1)
				message+=":";
		}
		this.message = ChatUtils.colorize(message);
	}

	@Override
	public boolean conditions() {
		return true;
	}

	@Override
	public CompleteStatus action() {
		Team t = MineQuest.questManager.getQuest(getQuestId()).getTeam();
		for (Player p : t.getPlayers())
			p.sendMessage(ChatColor.YELLOW + "[QUEST] " + ChatColor.WHITE + message);
		return CompleteStatus.SUCCESS;
	}
	
	

}
