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
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Player.PlayerDetails;
import com.theminequest.MineQuest.Backend.BackendFailedException;
import com.theminequest.MineQuest.Backend.GroupBackend;
import com.theminequest.MineQuest.Backend.QuestAvailability;
import com.theminequest.MineQuest.Backend.QuestBackend;
import com.theminequest.MineQuest.Group.Group;
import com.theminequest.MineQuest.Group.Team;


public class CommandListener implements CommandExecutor{

	public CommandListener(){
		MineQuest.log("[CommandFrontend] Starting Command Frontend...");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
		Player player = null;
		if (sender instanceof Player)
			player = (Player) sender;

		if (args.length == 0){
			if(cmd.getName().equalsIgnoreCase("minequest")){
				showMineQuestHelp(sender);
				return true;
			}
			else if(cmd.getName().equalsIgnoreCase("party") && player != null){
				showPartyHelp(sender);
				return true;
			}
			else if(cmd.getName().equalsIgnoreCase("quest") && player != null){
				sender.sendMessage("Quest Commands:");
				sender.sendMessage("    /quest list");
				sender.sendMessage("    /quest start <name of quest> - start a quest with party.");
				sender.sendMessage("    /quest quit - quit the instance of quest, lose current exp.");
				return true;
			}
		}

		//Quest Core Commands			

		/*Party Commands:
		 * party create
		 * party invite [player name]
		 * party list
		 * party leave
		 */

		if(command.equalsIgnoreCase("party")){
			Group t = GroupBackend.getCurrentGroup(Bukkit.getPlayer(sender.getName()));
			if(args[0].equalsIgnoreCase("create") && (t == null)){
				try {
					GroupBackend.createTeam(player);
				} catch (BackendFailedException e) {
					sender.sendMessage(e.toString());
				}
				sender.sendMessage("Created Party");
				return true;
			}

			else if(args[0].equalsIgnoreCase("invite") == true){

				if (args.length == 1){
					sender.sendMessage("You must specify a player");
					return true;
				}

				else if (args.length == 2){
					Player invitee = Bukkit.getPlayer(args[1]);

					if (args[1] == null){
						sender.sendMessage("You must specify a player.");
						return true;
					}

					else if (invitee == null){
						sender.sendMessage("That player is not online.");
						return true;
					}

					else if (GroupBackend.getCurrentGroup(invitee) != null){
						sender.sendMessage("Player is already in a group.");
						return true;
					}

					else if (invitee != null && (GroupBackend.getCurrentGroup(invitee) == null)){
						try {
							GroupBackend.invitePlayer(player, invitee);
						} catch (BackendFailedException e) {
							sender.sendMessage("Could not invite player: " + e.getMessage());
						}
						sender.sendMessage("Player invited to group");
						return true;
					}
				}
			}

			else if(args[0].equalsIgnoreCase("list") == true){
				Group team = GroupBackend.getCurrentGroup(player); 
				List<Player> players = team.getPlayers();
				sender.sendMessage(players.toString());
				return true;
			}

			else if(args[0].equalsIgnoreCase("leave")){
				// FIXME REFACTOR
				//GroupBackend.removePlayerFromTeam(player);
				sender.sendMessage("Removed from party");
				return true;
			}

			else{
				sender.sendMessage("Unknown Party Command.");
				return true;
			}
		}

		/*
		 * Quest Commands:
		 * 
		 * Quest List
		 * Quest start
		 * Quest Quit
		 */

		else if(command.equalsIgnoreCase("quest")){
			if(args[0].equalsIgnoreCase("start")){
				try {
					QuestBackend.isRepeatable(args[1]);
				} catch (IllegalArgumentException e) {
					sender.sendMessage("Quest no exist? Ask sysadmin :|");
				}
				// FIXME REFACTOR DELEGATED TO GROUPBACKEND :|
				return true;
			}
			else if((args[0].equalsIgnoreCase("start")) && (args.length != 2)){
				sender.sendMessage("Incorrect number of arguments!");
				return true;
			}

			else if(args[0].equalsIgnoreCase("quit")){
				// FIXME REFACTOR
				/*try {
					QuestBackend.cancelActiveQuest(player);
				} catch (BackendFailedException e) {
					sender.sendMessage(e.getMessage());
				}*/
				return true;
			}
			else if(args[0].equalsIgnoreCase("List")){
				try {
					List<String> questlist = QuestBackend.getQuests(QuestAvailability.AVAILABLE, player);
					String ql = questlist.toString();
					sender.sendMessage(ql);
				} catch (SQLException e) {
					MineQuest.log(Level.SEVERE, e.getMessage());
					sender.sendMessage("Could not find your Quest List");
				}
				return true;
			}
		}
		return false;
	}

	public void showMineQuestHelp(CommandSender sender){
		String[] message = {
				formatHeader("MineQuest Help"),
				formatHelp("quest", "List quest commands."),
				formatHelp("party", "List party commands.")
		};
		sender.sendMessage(message);
	}
	
	public void showPartyHelp(CommandSender sender){
		String[] message = {
				formatHeader("Party Help"),
				formatHelp("party create","Create a party."),
				formatHelp("party list", "List users in your party."),
				formatHelp("party accept", "Accept party invitation."),
				formatHelp("party join <user>", "Join a user's party."),
				formatHelp("party leave", "Depart your current party.")
		};
		
		sender.sendMessage(message);
	}
	
	private String formatHeader(String headername) {
		return ChatColor.GREEN + "==== { " + ChatColor.YELLOW + headername + ChatColor.GREEN + " } ====";
	}
	
	private String formatHelp(String command, String description) {
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
