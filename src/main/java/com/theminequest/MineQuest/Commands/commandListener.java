package com.theminequest.MineQuest.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commandListener implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		player = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("minequest")){ // If the player typed /basic then do the following...
			sender.sendMessage("Type /Quest for Quest help");
			sender.sendMessage("Type /Spell for spell help");
			sender.sendMessage("Type /Npc for npc help");
			sender.sendMessage("Type /Class for class help");
			sender.sendMessage("Type /Cubeconomy for Mq economy help");
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("quest") && player != null){
			sender.sendMessage("    /create_party - create a party.");
			sender.sendMessage("    /list_party - list users in your party.");
			sender.sendMessage("    /join_party <username> - join username's party.");
			sender.sendMessage("    /start_quest <name of quest> - start a quest with party.");
			sender.sendMessage("    /quit_quest - quit the instance of quest, lose current exp.");
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("spell") && player != null){
			sender.sendMessage("    /bind <spellname> - binds a spell to the item.");
			sender.sendMessage("    /unbind - list users in your party.");
			sender.sendMessage("    /spellcomp <username> - join username's party.");
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("npc") && player != null){
			sender.sendMessage("    /npc create <name> - creates a npc at your current location.");
			sender.sendMessage("    /npc select - selects the npc that you click on.");
			sender.sendMessage("    /npc path create - Starts the path creation.");
			sender.sendMessage("    /npc path point - Adds a point for the npcs path.");
			sender.sendMessage("    /npc path finish - Ends the path creation.");
			sender.sendMessage("    /npc message <#> <message> - Adds a message to the npc.");
			sender.sendMessage("    /npc property <property> <value> - Adds the given property.");
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("class") && player != null){ 
			sender.sendMessage("    /char - Shows your level.");
			sender.sendMessage("    /class select <Class name> - Sets your class to the given type.");
			sender.sendMessage("    /abillist - Lists all available spells for you class.");
			sender.sendMessage("    /class reset - Resets character to choose a new class.");
			sender.sendMessage("    /class reset confirm - used to confirm the reset.");
			sender.sendMessage("    Class resets can not be undone and will reset your lvl in that class.");
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("cubeconomy") && player != null){
			sender.sendMessage("    / - ");
			sender.sendMessage("    / - ");
			sender.sendMessage("    / - ");
			sender.sendMessage("    / - ");
			sender.sendMessage("    / - ");
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("") && player == null){
			sender.sendMessage("This must be run by a player.");
		}
		return false; 
	}
}
