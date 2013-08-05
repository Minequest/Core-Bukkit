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
package com.theminequest.bukkit.frontend.cmd;

import static com.theminequest.common.util.I18NMessage._;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.theminequest.api.Managers;
import com.theminequest.api.platform.ChatColor;

public abstract class CommandFrontend implements CommandExecutor {
	
	public static final String INOPERM = ChatColor.RED() + _("You don't have permission to do that.");
	public static final String IINVALID = ChatColor.YELLOW() + _("Invalid command. See the help menu for more information.");
	public static final String ISEVERE = ChatColor.RED() + _("A severe error occured on the command frontend.");
	public static final String INOPLAYER = ChatColor.YELLOW() + _("No such player found!");
	
	private String cmdname;
	
	public CommandFrontend(String name) {
		cmdname = name;
		Managers.log("[CommandFrontend] Starting Command Frontend for \"" + cmdname + "\"...");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player player = null;
		if (sender instanceof Player)
			player = (Player) sender;
		if ((player == null) && !allowConsole()) {
			Managers.log(Level.WARNING, "[CommandFrontend] No console use for \"" + cmdname + "\"...");
			return false;
		}
		
		if (args.length == 0) {
			noOptionSpecified(sender, args);
			return true;
		}
		
		String cmd = args[0].toLowerCase();
		
		if (!sender.hasPermission("minequest.command." + label + "." + cmd)) {
			sender.sendMessage(_("You have no permission to do that!"));
			return true;
		}
		
		String[] arguments = shrinkArray(args);
		
		Method m;
		try {
			if (cmd.equals("help")) {
				help(sender, arguments);
				return true;
			}
			if (!allowConsole()) {
				try {
					m = this.getClass().getMethod(cmd, Player.class, String[].class);
				} catch (NoSuchMethodException e) {
					try {
						m = this.getClass().getMethod(cmd, CommandSender.class, String[].class);
					} catch (NoSuchMethodException e1) {
						sender.sendMessage(IINVALID);
						return true;
					}
				}
				m.invoke(this, player, arguments);
			} else {
				try {
					m = this.getClass().getMethod(cmd, CommandSender.class, String[].class);
				} catch (NoSuchMethodException e) {
					sender.sendMessage(IINVALID);
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
		sender.sendMessage(ISEVERE);
		return true;
	}
	
	private String[] shrinkArray(String[] array) {
		if (array.length <= 1)
			return new String[0];
		return Arrays.copyOfRange(array, 1, array.length);
	}
	
	public abstract void help(CommandSender p, String[] args);
	
	public abstract void noOptionSpecified(CommandSender sender, String[] args);
	
	public abstract boolean allowConsole();
	
}
