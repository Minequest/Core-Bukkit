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
package com.theminequest.MQCoreEvents;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Events.DelayedQuestEvent;
import com.theminequest.MineQuest.API.Group.QuestGroup;
import com.theminequest.MineQuest.API.Utils.ChatUtils;

public class MessageEvent extends DelayedQuestEvent {
	
	private String message;
	private int delay;
	
	@Override
	public void parseDetails(String[] details) {
		delay = Integer.parseInt(details[0]);
		StringBuilder message = new StringBuilder();
		for (int i = 1; i < details.length; i++) {
			message.append(details[i]);
			if (i != (details.length - 1))
				message.append(':');
		}
		this.message = ChatUtils.colorize(message.toString());
	}
	
	@Override
	public boolean delayedConditions() {
		return true;
	}
	
	@Override
	public CompleteStatus action() {
		QuestGroup g = Managers.getQuestGroupManager().get(getQuest());
		for (Player p : g.getMembers())
			p.sendMessage(ChatColor.YELLOW + "[Quest] " + ChatColor.WHITE + message);
		return CompleteStatus.SUCCESS;
	}
	
	@Override
	public Integer switchTask() {
		return null;
	}
	
	@Override
	public long getDelay() {
		return delay;
	}
	
}
