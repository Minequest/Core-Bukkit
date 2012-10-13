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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.I18NMessage;
import com.theminequest.MineQuest.API.Managers;

public abstract class CommandFrontend implements CommandExecutor {
	
	public static final String INOPERM = "CMDNOPERM";
	public static final String IINVALID = "CMDINVALID";
	public static final String ISEVERE = "CMDSEVERE";
	public static final String INOPLAYER = "CMDNOPLAYER";
	
	private String cmdname;
	
	public CommandFrontend(String name){
		cmdname = name;
		Managers.log("[CommandFrontend] Starting Command Frontend for \""+cmdname+"\"...");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player player = null;
		if (sender instanceof Player)
			player = (Player)sender;
		if ((player==null) && !allowConsole()){
			Managers.log(Level.WARNING,"[CommandFrontend] No console use for \""+cmdname+"\"...");
			return false;
		}
		
		if (args.length==0) {
			noOptionSpecified(sender,args);
			return true;
		}
		
		String cmd = args[0].toLowerCase();
		
		if (!sender.hasPermission("minequest.command."+label+"."+cmd)) {
			sender.sendMessage(I18NMessage.getLocale().getString(INOPERM));
			return true;
		}
		
		String[] arguments = shrinkArray(args);
		
		Method m;
		try {
			if (cmd.equals("help")) {
				help(sender,arguments);
				return true;
			}
			if (!allowConsole()) {
				try {
					m = this.getClass().getMethod(cmd, Player.class, String[].class);
				} catch (NoSuchMethodException e) {
					try {
						m = this.getClass().getMethod(cmd, CommandSender.class, String[].class);
					} catch (NoSuchMethodException e1) {
						sender.sendMessage(I18NMessage.getLocale().getString(IINVALID));
						return true;
					}
				}
				m.invoke(this, player, arguments);
			} else {
				try {
					m = this.getClass().getMethod(cmd, CommandSender.class, String[].class);
				} catch (NoSuchMethodException e) {
					sender.sendMessage(I18NMessage.getLocale().getString(IINVALID));
					return true;
				}
				m.invoke(this, sender, arguments);
			}
			return true;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		sender.sendMessage(I18NMessage.getLocale().getString(ISEVERE));
		return true;
	}
	
	private String[] shrinkArray(String[] array){
		if (array.length<=1)
			return new String[0];
		return Arrays.copyOfRange(array, 1, array.length);
	}
	
	public abstract void help(CommandSender p, String[] args);
	public abstract void noOptionSpecified(CommandSender sender, String[] args);
	public abstract boolean allowConsole();
	
}
