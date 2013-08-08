package com.theminequest.bukkit.frontend.cmd;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.theminequest.api.Managers;
import com.theminequest.bukkit.frontend.inventory.MineQuestMenu;

public class MineQuestCommandFrontend implements CommandExecutor {
	
	public Map<String, String> helpmenu;
	
	public MineQuestCommandFrontend() {
		Managers.log("[CommandFrontend] Starting Command Frontend...");
		helpmenu = new LinkedHashMap<String, String>();
		helpmenu.put("quest", "List Quest Commands.");
		helpmenu.put("party", "List Party Commands.");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
		if (cmd.getName().equalsIgnoreCase("minequest")) {
			showMineQuestHelp(sender);
			return true;
		} if (cmd.getName().equalsIgnoreCase("mp")) {
			if (sender instanceof Player)
				MineQuestMenu.partyMenu((Player) sender);
		}
		
		return false;
	}
	
	public void showMineQuestHelp(CommandSender sender) {
		String[] msg = new String[helpmenu.size() + 2];
		msg[0] = formatHeader("MineQuest Help");
		Iterator<String> entries = helpmenu.keySet().iterator();
		int counter = 1;
		while (entries.hasNext()) {
			String entry = entries.next();
			msg[counter] = formatHelp(entry, helpmenu.get(entry));
			counter++;
		}
		msg[msg.length - 1] = formatHeader("Core Version: " + Managers.getVersion());
		sender.sendMessage(msg);
	}
	
	private String formatHeader(String headername) {
		return ChatColor.GREEN + "==== { " + ChatColor.YELLOW + headername + ChatColor.GREEN + " } ====";
	}
	
	private String formatHelp(String command, String description) {
		String toreturn = "";
		toreturn += ChatColor.GREEN + "/" + command;
		for (int i = 0; i < (20 - command.length()); i++)
			toreturn += " ";
		for (int i = 0; i < 10; i++)
			toreturn += " ";
		for (int i = 0; i < (30 - description.length()); i++)
			toreturn += " ";
		toreturn += ChatColor.YELLOW + description;
		return toreturn;
	}
}
