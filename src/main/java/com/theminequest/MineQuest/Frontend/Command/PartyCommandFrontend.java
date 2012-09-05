/*
 * This file is part of MineQuest, The ultimate MMORPG plugin!.
 * MineQuest is licensed under GNU General Public License v3.
 * Copyright (C) 2012 The MineQuest Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.theminequest.MineQuest.Frontend.Command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.I18NMessage;
import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.API.ManagerException;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Group.Group;
import com.theminequest.MineQuest.API.Group.GroupException;
import com.theminequest.MineQuest.API.Group.GroupException.GroupReason;
import com.theminequest.MineQuest.API.Utils.ChatUtils;

public class PartyCommandFrontend extends CommandFrontend {

	public PartyCommandFrontend() {
		super("party");
	}

	public Boolean accept(Player p, String[] args) {
		if (Managers.getGroupManager().indexOf(p)!=-1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Party_INPARTY.getValue());
			return false;
		}
		if (!Managers.getGroupManager().hasInvite(p)){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Party_NOINVITE.getValue());
			return false;
		}
		try {
			Managers.getGroupManager().acceptInvite(p);
			p.sendMessage(I18NMessage.Cmd_Party_ACCEPT.getValue());
			return true;
		} catch (ManagerException e) {
			e.printStackTrace();
			p.sendMessage("Exception occured: " + e.toString());
			return false;
		}
	}

	public Boolean create(Player p, String[] args) {
		if (Managers.getGroupManager().indexOf(p)!=-1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Party_INPARTY.getValue());
			return false;
		}
		if (Managers.getGroupManager().hasInvite(p)){
			p.sendMessage(ChatColor.GRAY + I18NMessage.Cmd_Party_DISCARD.getValue());
		}
		Managers.getGroupManager().createNewGroup(p);
		p.sendMessage(I18NMessage.Cmd_Party_CREATE.getValue());
		return true;
	}

	public Boolean invite(Player p, String[] args) {
		if (Managers.getGroupManager().indexOf(p)==-1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOPARTY.getValue());
			return false;
		}
		if (args.length!=1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_INVALIDARGS.getValue());
			return false;
		}
		Group g = Managers.getGroupManager().get(p);
		if (!g.getLeader().equals(p)){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOTLEADER.getValue());
			return false;
		}

		Player mate = Bukkit.getPlayerExact(args[0]);
		if (mate==null){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOSUCHPLAYER.getValue());
			return false;
		}
		if (Managers.getGroupManager().indexOf(mate)!=-1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Party_TARGETINPARTY.getValue());
			return false;
		}
		try {
			Managers.getGroupManager().invite(mate,g);
			p.sendMessage(I18NMessage.Cmd_Party_TARGETINVITESENT.getValue());
			return true;
		} catch (ManagerException e) {
			e.printStackTrace();
			p.sendMessage("Error occurred trying to send message: " + e.getMessage());
			return false;
		}
	}

	public Boolean kick(Player p, String[] args) {
		if (Managers.getGroupManager().indexOf(p)==-1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOPARTY.getValue());
			return false;
		}
		if (args.length!=1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_INVALIDARGS.getValue());
			return false;
		}
		Group g = Managers.getGroupManager().get(p);
		if (!g.getLeader().equals(p)){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOTLEADER.getValue());
			return false;
		}
		Player mate = Bukkit.getPlayer(args[0]);
		if (!g.contains(mate)){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Party_TARGETNOPARTY.getValue());
			return false;
		}

		try {
			g.remove(mate);
			p.sendMessage(I18NMessage.Cmd_Party_KICK + mate.getDisplayName());
			mate.sendMessage(I18NMessage.Cmd_Party_KICKTARGET + " (" + p.getDisplayName() + ")");
			return true;
		} catch (GroupException e) {
			p.sendMessage(ChatColor.GRAY + "ERR: " + e.getMessage());
			e.printStackTrace();
			return false;
		}

	}

	public Boolean leave(Player p, String[] args) {
		if (Managers.getGroupManager().indexOf(p)==-1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOPARTY.getValue());
			return false;
		}
		if (args.length!=0){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_INVALIDARGS.getValue());
			return false;
		}
		Group g = Managers.getGroupManager().get(p);
		boolean leader = g.getLeader().equals(p);

		try {
			if (leader && g.getMembers().size()!=1)
				g.setLeader(g.getMembers().get(1));
			g.remove(p);
			p.sendMessage(I18NMessage.Cmd_Party_LEAVE.getValue());
			return true;
		} catch (GroupException e) {
			p.sendMessage(ChatColor.GRAY + "ERR: " + e.getMessage());
			e.printStackTrace();
			return false;
		}

	}

	public Boolean list(Player p, String[] args) {
		if (Managers.getGroupManager().indexOf(p)==-1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOPARTY.getValue());
			return false;
		}
		if (args.length!=0){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_INVALIDARGS.getValue());
			return false;
		}
		Group g = Managers.getGroupManager().get(p);
		List<Player> members = g.getMembers();
		List<String> messages = new ArrayList<String>();
		messages.add(ChatUtils.formatHeader(I18NMessage.Cmd_Party_LIST.getValue() + " " + members.size() + "/" + g.getCapacity()));
		for (Player m : members) {
			if (g.getLeader().equals(m))
				messages.add(ChatColor.RED + m.getName() + ChatColor.GRAY + " : Lvl " + m.getLevel() + ", Health " + m.getHealth() + "/" + m.getMaxHealth());
			else
				messages.add(ChatColor.AQUA + m.getName() + ChatColor.GRAY + " : Lvl " + m.getLevel() + ", Health " + m.getHealth() + "/" + m.getMaxHealth());
		}

		for (String m : messages) {
			p.sendMessage(m);
		}

		return true;
	}

	public Boolean promote(Player p, String[] args) {
		if (Managers.getGroupManager().indexOf(p)==-1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOPARTY.getValue());
			return false;
		}
		if (args.length!=1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_INVALIDARGS.getValue());
			return false;
		}
		Group g = Managers.getGroupManager().get(p);
		if (!g.getLeader().equals(p)){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOTLEADER.getValue());
			return false;
		}
		Player mate = Bukkit.getPlayer(args[0]);
		if (!g.contains(mate)){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Party_TARGETNOPARTY.getValue());
			return false;
		}

		try {
			g.setLeader(mate);
			mate.sendMessage(I18NMessage.Cmd_Party_PROMOTETARGET.getValue());
			p.sendMessage(I18NMessage.Cmd_Party_PROMOTE.getValue());
			return true;
		} catch (GroupException e) {
			p.sendMessage(ChatColor.GRAY + "ERR: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void help(CommandSender sender, String[] args) {
		Player p = (Player)sender;
		/*
		 * accept
		 * create
		 * 
		 * info
		 * invite
		 * kick
		 * leave
		 * list
		 * promote
		 */

		List<String> messages = new ArrayList<String>();
		Group g = null;
		boolean invite = Managers.getGroupManager().hasInvite(p);
		boolean isLeader = false;
		if (Managers.getGroupManager().indexOf(p)!=-1){
			g = Managers.getGroupManager().get(p);
			isLeader = g.getLeader().equals(p);
		}

		messages.add(ChatUtils.formatHeader(I18NMessage.Cmd_Party_HELP.getValue()));
		if (g==null){
			if (invite)
				messages.add(ChatUtils.formatHelp("party accept", I18NMessage.Cmd_Party_HELPINVITE.getValue()));
			else
				messages.add(ChatColor.GRAY + I18NMessage.Cmd_Party_NOINVITE.getValue());
			messages.add(ChatUtils.formatHelp("party create", I18NMessage.Cmd_Party_HELPCREATE.getValue()));
			messages.add(ChatColor.AQUA + I18NMessage.Cmd_NOPARTY.getValue());
		} else {
			if (isLeader){
				messages.add(ChatUtils.formatHelp("party invite <name>", I18NMessage.Cmd_Party_HELPINVITETARGET.getValue()));
				messages.add(ChatUtils.formatHelp("party kick <name>", I18NMessage.Cmd_Party_HELPKICK.getValue()));
			} else {
				messages.add(ChatColor.GRAY + "[party invite] " + I18NMessage.Cmd_NOTLEADER.getValue());
				messages.add(ChatColor.GRAY + "[party kick] " + I18NMessage.Cmd_NOTLEADER.getValue());
			}
			messages.add(ChatUtils.formatHelp("party leave", I18NMessage.Cmd_Party_HELPLEAVE.getValue()));
			messages.add(ChatUtils.formatHelp("party list", I18NMessage.Cmd_Party_HELPLIST.getValue()));
			if (isLeader)
				messages.add(ChatUtils.formatHelp("party promote <name>", I18NMessage.Cmd_Party_HELPPROMOTE.getValue()));
			else
				messages.add(ChatColor.GRAY + "[party promote] " + I18NMessage.Cmd_NOTLEADER.getValue());
		}

		for (String s : messages)
			p.sendMessage(s);
	}

	@Override
	public boolean allowConsole() {
		return false;
	}

	@Override
	public void noOptionSpecified(CommandSender sender, String[] args) {
		help(sender,args);
	}

}
