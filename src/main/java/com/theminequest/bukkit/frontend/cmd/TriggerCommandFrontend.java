package com.theminequest.bukkit.frontend.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TriggerCommandFrontend implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player) {
			if (!((Player)arg0).isOp())
				return false;
		}
		
		if (arg3.length != 3) {
			arg0.sendMessage("Invalid Arguments to Trigger: [playerName] [questName] [true/false]");
			return true;
		}
		
		String playerName = arg3[0];
		String questName = arg3[1];
		boolean tF = arg3[2].equalsIgnoreCase("true");
		
		Bukkit.getPluginManager().callEvent(new CommandTriggerEvent(playerName, questName, tF));
		return true;
	}
	
}
