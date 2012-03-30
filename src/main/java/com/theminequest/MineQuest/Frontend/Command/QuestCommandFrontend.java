package com.theminequest.MineQuest.Frontend.Command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Utils.ChatUtils;

public class QuestCommandFrontend implements CommandExecutor {
	
	public QuestCommandFrontend(){
		MineQuest.log("[CommandFrontend] Starting Command Frontend for \"quest\"...");
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (!(arg0 instanceof Player)){
			MineQuest.log(Level.WARNING,"[CommandFrontend] No console use for \"quest\"...");
			return false;
		}
		Player player = (Player)arg0;
		
		if (arg2.equals(""))
			return help(player,arg3);
		
		try {
			Method m = this.getClass().getMethod(arg2, Player.class, String[].class);
			return (Boolean)m.invoke(this, player, arg3);
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean help(Player p, String[] args) {
		String[] message = {
				ChatUtils.formatHeader("Quest Help"),
				ChatUtils.formatHelp("quest abandon", "Abandon active quest."),
				ChatUtils.formatHelp("quest accept <name>", "Accept a certain quest."),
				ChatUtils.formatHelp("quest accepted", "List accepted (pending) quests."),
				ChatUtils.formatHelp("quest active", "View active quest."),
				ChatUtils.formatHelp("quest available", "List available quests."),
				ChatUtils.formatHelp("quest start <name>", "Begin a quest."),
				ChatUtils.formatHelp("quest enter", "Enter active quest"),
				ChatUtils.formatHelp("quest exit", "Exit an active quest"),
				ChatColor.AQUA + "Remember, starting a quest requires a party.",
		};
		
		p.sendMessage(message);
		
		return true;
	}

}
