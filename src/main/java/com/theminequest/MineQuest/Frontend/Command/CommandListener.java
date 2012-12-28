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
package com.theminequest.MineQuest.Frontend.Command;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.API.Managers;


public class CommandListener implements CommandExecutor{

	public Map<String,String> helpmenu;

	public CommandListener(){
		Managers.log("[CommandFrontend] Starting Command Frontend...");
		helpmenu = new LinkedHashMap<String,String>();
		helpmenu.put("quest", "List Quest Commands.");
		helpmenu.put("party", "List Party Commands.");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
		if(cmd.getName().equalsIgnoreCase("minequest")){
			showMineQuestHelp(sender);
			return true;
		}

		return false;
	}

	public void showMineQuestHelp(CommandSender sender){
		String[] msg = new String[helpmenu.size()+2];
		msg[0] = formatHeader("MineQuest Help");
		Iterator<String> entries = helpmenu.keySet().iterator();
		int counter = 1;
		while (entries.hasNext()){
			String entry = entries.next();
			msg[counter] = formatHelp(entry,helpmenu.get(entry));
			counter++;
		}
		msg[msg.length-1] = formatHeader("Core Version: " + MineQuest.getVersion());
		sender.sendMessage(msg);
	}

	private String formatHeader(String headername) {
		return ChatColor.GREEN + "==== { " + ChatColor.YELLOW + headername + ChatColor.GREEN + " } ====";
	}

	private String formatHelp(String command, String description) {
		String toreturn = "";
		toreturn += ChatColor.GREEN + "/" + command;
		for (int i=0; i<20-command.length(); i++)
			toreturn+=" ";
		for (int i=0; i<10; i++)
			toreturn+=" ";
		for (int i=0; i<30-description.length(); i++)
			toreturn+=" ";
		toreturn += ChatColor.YELLOW + description;
		return toreturn;
	}
}
