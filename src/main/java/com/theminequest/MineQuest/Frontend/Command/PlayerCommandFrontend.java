package com.theminequest.MineQuest.Frontend.Command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Player.PlayerDetails;
import com.theminequest.MineQuest.Player.PlayerManager;
import com.theminequest.MineQuest.Utils.ChatUtils;

public class PlayerCommandFrontend extends CommandFrontend {

	public PlayerCommandFrontend() {
		super("player");
	}
	
	// TODO Implement localization fully.
	
	public Boolean giveexp(Player p, String[] args){
		if (p!=null)
			return false;
		
		ConsoleCommandSender c = Bukkit.getConsoleSender();
		
		if (args.length!=2){
			c.sendMessage("Invalid number of arguments.");
			return false;
		}
		
		Player player = Bukkit.getPlayerExact(args[0]);
		if (player==null){
			c.sendMessage("No such player. :(");
			return false;
		}
		
		PlayerDetails details = MineQuest.playerManager.getPlayerDetails(player);
		details.modifyExperienceBy((int)Double.parseDouble(args[1]));
		return true;
	}
	
	public Boolean levelup(Player p, String[] args){
		if (p!=null)
			return false;
		
		ConsoleCommandSender c = Bukkit.getConsoleSender();
		
		if (args.length!=1){
			c.sendMessage("Invalid number of arguments.");
			return false;
		}
		
		Player player = Bukkit.getPlayerExact(args[0]);
		if (player==null){
			c.sendMessage("No such player. :(");
			return false;
		}
		
		PlayerDetails details = MineQuest.playerManager.getPlayerDetails(player);
		details.levelUp();
		return true;
	}
	
	public Boolean info(Player p, String[] args) {
		CommandSender c;
		if (p==null)
			c = Bukkit.getConsoleSender();
		else
			c = p;
		
		if (args.length>1){
			c.sendMessage("Invalid number of arguments.");
			return false;
		}
		
		Player lookup;
		
		if (args.length==0)
			lookup = p;
		else
			lookup = Bukkit.getPlayerExact(args[0]);
		
		if (lookup==null){
			c.sendMessage("No such player.");
			return false;
		}
		
		PlayerDetails details = MineQuest.playerManager.getPlayerDetails(lookup);
		
		List<String> messages = new ArrayList<String>();
		messages.add(ChatUtils.formatHeader("Player Information: " + lookup.getName()));
		messages.add(ChatColor.AQUA + "Display Name: " + ChatColor.WHITE + lookup.getDisplayName());
		messages.add(ChatColor.AQUA + "On Team: " + ChatColor.WHITE + (MineQuest.groupManager.indexOf(lookup)!=-1));
		messages.add(ChatColor.AQUA + "Health: " + ChatColor.WHITE + lookup.getHealth());
		messages.add(ChatColor.AQUA + "Level: " + ChatColor.WHITE + details.getLevel());
		if (p.equals(lookup) || !(c instanceof Player)){
			messages.add(ChatColor.AQUA + "Exp: " + ChatColor.WHITE + details.getExperience() + "/" + (PlayerManager.BASE_EXP*details.getLevel()));
			messages.add(ChatColor.AQUA + "Mana: " + ChatColor.WHITE + details.getMana() + "/" + (PlayerManager.BASE_MANA*details.getLevel()));
		}
		
		for (String s: messages){
			c.sendMessage(s);
		}
		return true;
	}

	@Override
	public Boolean help(Player p, String[] args) {
		List<String> messages = new ArrayList<String>();
		
		// CONSOLE COMMANDS
		if (p==null){
			messages.add(ChatUtils.formatHeader(localization.getString("player_help_CONSOLE","Console Commands")));
			messages.add(ChatUtils.formatHelp("player giveexp [name] [amt]", localization.getString("player_help_giveexp", "Give player exp.")));
			messages.add(ChatUtils.formatHelp("player levelup [name]", localization.getString("player_help_levelup","Level up player by 1.")));
		}
		messages.add(ChatUtils.formatHeader(localization.getString("player_help","Player Commands")));
		messages.add(ChatUtils.formatHelp("player info <name>", localization.getString("player_help_info","Retrieve player information (or your own if you omit name).")));		
		if (p==null){
			CommandSender c = Bukkit.getConsoleSender();
			for (String s : messages){
				c.sendMessage(s);
			}
		} else {
			for (String s : messages){
				p.sendMessage(s);
			}
		}
		return true;
	}

	@Override
	public boolean allowConsole() {
		return true;
	}

}
