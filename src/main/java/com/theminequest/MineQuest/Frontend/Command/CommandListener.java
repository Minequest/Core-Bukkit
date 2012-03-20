/**
 * This file, CommandListener.java, is part of MineQuest:
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
 **/
package com.theminequest.MineQuest.Frontend.Command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.Team.Team;
import com.theminequest.MineQuest.Team.TeamManager;

public class CommandListener implements CommandExecutor{
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
			sender.sendMessage("    /party create - create a party.");
			sender.sendMessage("    /party list - list users in your party.");
			sender.sendMessage("    /party join <username> - join username's party.");
			sender.sendMessage("    /party quit - removes you from the party.");
			sender.sendMessage("    /quest start <name of quest> - start a quest with party.");
			sender.sendMessage("    /quest quit - quit the instance of quest, lose current exp.");
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
			sender.sendMessage("    /spells - Lists all available spells for you class.");
			sender.sendMessage("    /class reset - Resets character to choose a new class.");
			sender.sendMessage("    /class reset confirm - used to confirm the reset.");
			sender.sendMessage("    Class resets can not be undone and will reset your lvl in that class.");
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("char") && player !=null){
			sender.sendMessage("Level: ");
			//TODO: Get Class
			//TODO: Get Class lvl
		}
		//Quest Core Commands
		if(cmd.getName().startsWith("party")){
			String partyCommand = cmd.getName().substring(7);
			Player partyMember = Bukkit.getPlayer(sender.getName());
			if(partyCommand == "create"){
				
				sender.sendMessage("CreatedParty");
			}
			if(partyCommand.contains("invite") == true){
				String memberToInvite = cmd.getName().substring(13);
				
				//TODO:Add member to party if there is room. 
			}
			if(partyCommand.contains("list") == true){
				//TODO:List party members.
			}
			if(partyCommand.equalsIgnoreCase("quit")){
				//TODO:Quit Party
			}
		}
		
		if(cmd.getName().startsWith("quest")){
			String questCommand = cmd.getName().substring(7);
			if(questCommand.contains("start")){
				String questName = cmd.getName().substring(12);
				//Check if quest exists. 
			}
			if(questCommand.contains("quit")){
				//Check what quest the player is currently in.
				//Quit quest.
			}
		}
		
		//Skill Related Commands
		if(cmd.getName().startsWith("class select") && player !=null){
			String className = cmd.getName().substring(13);
			String playername = sender.getName();
			if(className.equalsIgnoreCase("warmage") == true){
				//TODO: Set player's class to WarMage.
			}
			if(className.equalsIgnoreCase("peacemage") == true){
				//TODO: Set player's class to PeaceMage.
			}
			if(className.equalsIgnoreCase("warrior") == true){
				//TODO: Set player's class to Warrior.
			}
			if(className.equalsIgnoreCase("archer") == true){
				//TODO: Set player's class to Archer.
			}
		}
		
		if(cmd.getName().startsWith("spells") == true){
			String className = ""; //Get player's class
			//TODO: Send player list of spells.
		}
		
		if(cmd.getName().equalsIgnoreCase("class reset")){
			String playerName = cmd.getName();
			sender.sendMessage("Please type /class reset confirm");
		}
		
		if(cmd.getName().equalsIgnoreCase("class reset confirm")){
			String playerName = cmd.getName();
			//TODO: Reset Class for player. 
			sender.sendMessage("Reseting Character");
			sender.sendMessage("To chose a class type /class select <class>");	
		}
		
		//NPC Related Commands
		if(cmd.getName().startsWith("npc ")){
			String npcCommand = cmd.getName().substring(5);
			if(npcCommand.equalsIgnoreCase("create")){
				String npcName = cmd.getName().substring(12);
				Location npcLocation = player.getLocation();
				//Spawn npc at this location. 
			}
			
			if(npcCommand.equalsIgnoreCase("select")){
				String npcName = cmd.getName().substring(12);
				//Select Npc for use with other commands.
			}
			
		}
		return false; 
	}
}
