/**
 * This file, CommandListener.java, is part of MineQuest:
 * A full featured and customizable quest/mission system.
 * Copyright (C) 2012 The MineQuest Party
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
package com.theminequest.MineQuest.Frontend.Command;

import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Group.Group;
import com.theminequest.MineQuest.Group.Party;


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
		String[] msg = new String[helpmenu.size()+1];
		msg[0] = formatHeader("MineQuest Help");
		Iterator<String> entries = helpmenu.keySet().iterator();
		int counter = 1;
		while (entries.hasNext()){
			String entry = entries.next();
			msg[counter] = formatHelp(entry,helpmenu.get(entry));
			counter++;
		}
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
