package com.theminequest.MineQuest.Frontend.Command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Backend.GroupBackend;
import com.theminequest.MineQuest.Group.Group;
import com.theminequest.MineQuest.Quest.Quest;
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

		if (arg3.length < 2)
			return help(player,arg3);

		String cmd = arg3[0];

		String[] arguments = Arrays.copyOfRange(arg3, 1, arg3.length-1);

		try {
			Method m = this.getClass().getMethod(cmd, Player.class, String[].class);
			return (Boolean)m.invoke(this, player, arguments);
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean help(Player p, String[] args) {

		List<String> messages = new ArrayList<String>();
		boolean inGroup = false;
		boolean isLeader = false;
		boolean inQuest = false;
		Quest active = null;
		if (GroupBackend.teamID(p)!=-1){
			Group g = GroupBackend.getCurrentGroup(p);
			inGroup = true;
			isLeader = g.getLeader().equals(p);
			inQuest = g.isInQuest();
			active = g.getQuest();
		}

		messages.add(ChatUtils.formatHeader("Quest Commands"));
		messages.add(ChatUtils.formatHelp("quest accept <name>", "Accept a certain quest."));
		messages.add(ChatUtils.formatHelp("quest accepted", "List accepted (pending) quests."));
		messages.add(ChatUtils.formatHelp("quest available", "List available quests."));

		if (inGroup){
			if (active != null && isLeader)
				messages.add(ChatUtils.formatHelp("quest abandon", "Abandon active quest."));
			else if (active != null)
				messages.add(ChatColor.GRAY + "[quest abandon] not leader!");
			else
				messages.add(ChatColor.GRAY + "[quest abandon] no active quest...");
			if (active!=null)
				messages.add(ChatUtils.formatHelp("quest active", "View active quest."));
			else
				messages.add(ChatColor.GRAY + "[quest active] no active quest...");
			if (active!=null && !inQuest)
				messages.add(ChatUtils.formatHelp("quest enter", "Enter active quest."));
			else if (active!=null && inQuest)
				messages.add(ChatUtils.formatHelp("quest exit", "Exit active quest."));
			else
				messages.add(ChatColor.GRAY + "[quest enter/exit] no active quest...");
			if (active == null && isLeader)
				messages.add(ChatUtils.formatHelp("quest start <name>", "Begin a quest."));
			else if (active == null)
				messages.add(ChatColor.GRAY + "[quest start] not leader!");
			else
				messages.add(ChatColor.GRAY + "[quest start] already on a quest...");
		} else
			messages.add(ChatColor.AQUA + "See the rest of the options by joining a group!");

		for (String m : messages) {
			p.sendMessage(m);
		}

		return true;
	}

}
