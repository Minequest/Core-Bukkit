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

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Player.PlayerDetails;
import com.theminequest.MineQuest.Team.Team;
import com.theminequest.MineQuest.Backend.TeamBackend;
import com.theminequest.MineQuest.Backend.QuestAvailability;
import com.theminequest.MineQuest.Backend.QuestBackend;


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
		
		//Don't worry about this command for now. 
		if(cmd.getName().equalsIgnoreCase("char") && player !=null){
			sender.sendMessage("Class: " + "");
			sender.sendMessage("Level: " + "");			
			//TODO: Get Class lvl
			return true;
		}
		
		//Quest Core Commands
		if(cmd.getName().startsWith("party")){
			String partyCommand = cmd.getName().substring(7);
			Team t = TeamBackend.getCurrentTeam(Bukkit.getPlayer(sender.getName()));
			
			if((partyCommand == "create") && (t == null)){
				//TODO: Create team. 
				sender.sendMessage("CreatedParty");
				return true;
			}
			
			if(partyCommand.contains("invite") == true){
				long teamID= TeamBackend.teamID(player); 
				Player invitee = Bukkit.getPlayer(cmd.getName().substring(13));
				
				if (invitee == null){
					sender.sendMessage("Could not find player.");
				}
				if (TeamBackend.getCurrentTeam(invitee) != null){
					sender.sendMessage("Player is already in a group.");
					return true;
				}
				if (invitee != null && (TeamBackend.getCurrentTeam(invitee) == null)){
					TeamBackend.invitePlayer(player, invitee, teamID);
					sender.sendMessage("Player invited to group");
					return true;
				}
			}
			if(partyCommand.contains("list") == true){
				Team team = TeamBackend.getCurrentTeam(player); 
				List<Player> players = team.getPlayers();
				sender.sendMessage(players.toString());
				return true;
			}
			if(partyCommand.equalsIgnoreCase("leave")){
				t.remove(player);
				sender.sendMessage("Removed from party");
				return true;
			}
			else{
				sender.sendMessage("Unknown Party Command.");
			}
		}
		
		if(cmd.getName().startsWith("quest")){
			String questCommand = cmd.getName().substring(7);
			if(questCommand.contains("start")){
				String questName = cmd.getName().substring(12);
				//Just In case the file was deleted. 
				File f = new File(MineQuest.activePlugin.getDataFolder()+File.separator+"quests"+File.separator+questName+".quest");
				if (f.exists() != true){
					//TODO: Start quest. Needed in the QuestBackend class.
				}
			}
			if(questCommand.equalsIgnoreCase("quit")){
				try {
					List<String> questlist = QuestBackend.getQuests(QuestAvailability.ACCEPTED, player);
					//TODO: Quit quest. Needed in the QuestBackend class.
				} catch (SQLException e) {
					sender.sendMessage("No quest found");
				}
			}
			if(questCommand.equals("List")){
				try {
					List<String> questlist = QuestBackend.getQuests(QuestAvailability.AVAILABLE, player);
					String ql = questlist.toString();
					sender.sendMessage(ql);
				} catch (SQLException e) {
					MineQuest.log(Level.SEVERE, e.toString());
					sender.sendMessage("Could not find your Quest List");
				}
			}
		}
	return false;
	}
}