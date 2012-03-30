/*
 * This file, ChatUtils.java, is part of MineQuest:
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
package com.theminequest.MineQuest.Utils;

import java.util.Calendar;

import org.bukkit.ChatColor;

public class ChatUtils {
	
    public static String replaceTime(String message) {
        Calendar calendar = Calendar.getInstance();

        if (message.contains("%h")) {
            message = message.replace("%h", String.format("%02d", calendar.get(Calendar.HOUR)));
        }

        if (message.contains("%H")) {
            message = message.replace("%H", String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)));
        }

        if (message.contains("%g")) {
            message = message.replace("%g", Integer.toString(calendar.get(Calendar.HOUR)));
        }

        if (message.contains("%G")) {
            message = message.replace("%G", Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)));
        }

        if (message.contains("%i")) {
            message = message.replace("%i", String.format("%02d", calendar.get(Calendar.MINUTE)));
        }

        if (message.contains("%s")) {
            message = message.replace("%s", String.format("%02d", calendar.get(Calendar.SECOND)));
        }

        if (message.contains("%a")) {
            message = message.replace("%a", (calendar.get(Calendar.AM_PM) == 0) ? "am" : "pm");
        }

        if (message.contains("%A")) {
            message = message.replace("%A", (calendar.get(Calendar.AM_PM) == 0) ? "AM" : "PM");
        }

        return message;
    }

    public static String colorize(String string) {
        if (string == null) {
            return "";
        }
        return string.replaceAll("&([a-z0-9])", "\u00A7$1");
    }
    
    /**
     * Chatify the string. Should be used, especially in cases where
     * we have localization files and they want different colours.
     * @param string String to chatify
     * @return Chatified string
     */
    public static String chatify(String string) {
    	string = colorize(string);
    	string = replaceTime(string);
    	return string;
    }
    
	public static String formatHeader(String headername) {
		return ChatColor.GREEN + "==== { " + ChatColor.YELLOW + headername + ChatColor.GREEN + " } ====";
	}
	
	public static String formatHelp(String command, String description) {
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
