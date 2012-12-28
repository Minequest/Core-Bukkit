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
import com.theminequest.MineQuest.API.ManagerException;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Group.Group;
import com.theminequest.MineQuest.API.Group.GroupException;
import com.theminequest.MineQuest.API.Utils.ChatUtils;

public class PartyCommandFrontend extends CommandFrontend {
	
	public static final String IINPARTY = "PARTYIN";
	public static final String IOUTPARTY = "PARTYOUT";
	public static final String INOINVITE = "PARTYNOINVITE";
	public static final String IACCEPT = "PARTYACCEPT";
	public static final String IDISCARD = "PARTYDISCARD";
	public static final String ICREATE = "PARTYCREATE";
	public static final String INOTLEADER = "PARTYNOTLEADER";
	public static final String ITARGETINPARTY = "PARTYTARGETIN";
	public static final String ITARGETOUTPARTY = "PARTYTARGETOUT";
	public static final String ITARGETINVITED = "PARTYTARGETSENT";
	public static final String IKICKED = "PARTYKICK";
	public static final String ITARGETKICKED = "PARTYTARGETKICK";
	public static final String ILEAVE = "PARTYLEAVE";
	public static final String ILIST = "PARTYLIST";
	public static final String IPROMOTE = "PARTYPROMOTE";

	public PartyCommandFrontend() {
		super("party");
	}

	public void accept(Player p, String[] args) {
		if (Managers.getGroupManager().indexOf(p)!=-1){
			p.sendMessage(I18NMessage.getLocale().getString(IINPARTY));
			return;
		}
		if (!Managers.getGroupManager().hasInvite(p)){
			p.sendMessage(I18NMessage.getLocale().getString(INOINVITE));
			return;
		}
		try {
			Managers.getGroupManager().acceptInvite(p);
			p.sendMessage(I18NMessage.getLocale().getString(IACCEPT));
		} catch (ManagerException e) {
			throw new RuntimeException(e); // toss this to CommandFrontend
		}
	}

	public void create(Player p, String[] args) {
		if (Managers.getGroupManager().indexOf(p)!=-1){
			p.sendMessage(I18NMessage.getLocale().getString(IINPARTY));
			return;
		}
		if (Managers.getGroupManager().hasInvite(p)){
			p.sendMessage(I18NMessage.getLocale().getString(IDISCARD));
		}
		Managers.getGroupManager().createNewGroup(p);
		p.sendMessage(I18NMessage.getLocale().getString(ICREATE));
	}

	public void invite(Player p, String[] args) {
		if (Managers.getGroupManager().indexOf(p)==-1){
			p.sendMessage(I18NMessage.getLocale().getString(IOUTPARTY));
			return;
		}
		if (args.length!=1){
			p.sendMessage(I18NMessage.getLocale().getString(CommandFrontend.IINVALID));
			return;
		}
		Group g = Managers.getGroupManager().get(p);
		if (!g.getLeader().equals(p)){
			p.sendMessage(I18NMessage.getLocale().getString(INOTLEADER));
			return;
		}

		Player mate = Bukkit.getPlayerExact(args[0]);
		if (mate==null){
			p.sendMessage(I18NMessage.getLocale().getString(CommandFrontend.INOPLAYER));
			return;
		}
		if (Managers.getGroupManager().indexOf(mate)!=-1){
			p.sendMessage(I18NMessage.getLocale().getString(ITARGETINPARTY));
			return;
		}
		try {
			Managers.getGroupManager().invite(mate,g);
			p.sendMessage(I18NMessage.getLocale().getString(ITARGETINVITED));
		} catch (ManagerException e) {
			throw new RuntimeException(e); // throw to CommandFrontend
		}
	}

	public void kick(Player p, String[] args) {
		if (Managers.getGroupManager().indexOf(p)==-1){
			p.sendMessage(I18NMessage.getLocale().getString(IOUTPARTY));
			return;
		}
		if (args.length!=1){
			p.sendMessage(I18NMessage.getLocale().getString(CommandFrontend.IINVALID));
			return;
		}
		Group g = Managers.getGroupManager().get(p);
		if (!g.getLeader().equals(p)){
			p.sendMessage(I18NMessage.getLocale().getString(INOTLEADER));
			return;
		}
		Player mate = Bukkit.getPlayer(args[0]);
		if (!g.contains(mate)){
			p.sendMessage(I18NMessage.getLocale().getString(ITARGETOUTPARTY));
			return;
		}

		try {
			g.remove(mate);
			p.sendMessage(I18NMessage.getLocale().getString(IKICKED).replace("%n%",mate.getDisplayName()));
			mate.sendMessage(I18NMessage.getLocale().getString(ITARGETKICKED).replace("%n%",p.getDisplayName()));
		} catch (GroupException e) {
			throw new RuntimeException(e); // toss to CommandFrontend
		}

	}

	public void leave(Player p, String[] args) {
		if (Managers.getGroupManager().indexOf(p)==-1){
			p.sendMessage(I18NMessage.getLocale().getString(IOUTPARTY));
			return;
		}
		if (args.length!=0){
			p.sendMessage(I18NMessage.getLocale().getString(CommandFrontend.IINVALID));
			return;
		}
		Group g = Managers.getGroupManager().get(p);
		boolean leader = g.getLeader().equals(p);

		try {
			if (leader && g.getMembers().size()!=1)
				g.setLeader(g.getMembers().get(1));
			g.remove(p);
			p.sendMessage(I18NMessage.getLocale().getString(ILEAVE));
		} catch (GroupException e) {
			throw new RuntimeException(e); // toss to CommandFrontend
		}

	}

	public void list(Player p, String[] args) {
		if (Managers.getGroupManager().indexOf(p)==-1){
			p.sendMessage(I18NMessage.getLocale().getString(IOUTPARTY));
			return;
		}
		if (args.length!=0){
			p.sendMessage(I18NMessage.getLocale().getString(CommandFrontend.IINVALID));
			return;
		}
		Group g = Managers.getGroupManager().get(p);
		List<Player> members = g.getMembers();
		List<String> messages = new ArrayList<String>();
		messages.add(ChatUtils.formatHeader(I18NMessage.getLocale().getString(ILIST) + " " + members.size() + "/" + g.getCapacity()));
		for (Player m : members) {
			if (g.getLeader().equals(m))
				messages.add(ChatColor.RED + m.getName() + ChatColor.GRAY + " : Lvl " + m.getLevel() + ", Health " + m.getHealth() + "/" + m.getMaxHealth());
			else
				messages.add(ChatColor.AQUA + m.getName() + ChatColor.GRAY + " : Lvl " + m.getLevel() + ", Health " + m.getHealth() + "/" + m.getMaxHealth());
		}

		for (String m : messages) {
			p.sendMessage(m);
		}
	}

	public void promote(Player p, String[] args) {
		if (Managers.getGroupManager().indexOf(p)==-1){
			p.sendMessage(I18NMessage.getLocale().getString(IOUTPARTY));
			return;
		}
		if (args.length!=1){
			p.sendMessage(I18NMessage.getLocale().getString(CommandFrontend.IINVALID));
			return;
		}
		Group g = Managers.getGroupManager().get(p);
		if (!g.getLeader().equals(p)){
			p.sendMessage(I18NMessage.getLocale().getString(INOTLEADER));
			return;
		}
		Player mate = Bukkit.getPlayer(args[0]);
		if (!g.contains(mate)){
			p.sendMessage(I18NMessage.getLocale().getString(ITARGETOUTPARTY));
			return;
		}

		try {
			g.setLeader(mate);
			for (Player member : g.getMembers()) {
				member.sendMessage(I18NMessage.getLocale().getString(IPROMOTE));
			}
		} catch (GroupException e) {
			throw new RuntimeException(e); // throw to CommandFrontend
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

		messages.add(ChatUtils.formatHeader(I18NMessage.getLocale().getString("PHELPHEAD")));
		if (g==null){
			if (invite)
				messages.add(ChatUtils.formatHelp("party accept", I18NMessage.getLocale().getString("PHELPACCEPT")));
			else
				messages.add(ChatColor.GRAY + I18NMessage.getLocale().getString(INOINVITE));
			messages.add(ChatUtils.formatHelp("party create", I18NMessage.getLocale().getString("PHELPCREATE")));
			messages.add(ChatColor.AQUA + I18NMessage.getLocale().getString("PHELPHINT"));
		} else {
			if (isLeader){
				messages.add(ChatUtils.formatHelp("party invite <name>", I18NMessage.getLocale().getString("PHELPINVITE")));
				messages.add(ChatUtils.formatHelp("party kick <name>", I18NMessage.getLocale().getString("PHELPKICK")));
			} else {
				messages.add(ChatColor.GRAY + "[party invite] " + I18NMessage.getLocale().getString(INOTLEADER));
				messages.add(ChatColor.GRAY + "[party kick] " + I18NMessage.getLocale().getString(INOTLEADER));
			}
			messages.add(ChatUtils.formatHelp("party leave", I18NMessage.getLocale().getString("PHELPLEAVE")));
			messages.add(ChatUtils.formatHelp("party list", I18NMessage.getLocale().getString("PHELPLIST")));
			if (isLeader)
				messages.add(ChatUtils.formatHelp("party promote <name>", I18NMessage.getLocale().getString("PHELPPROMOTE")));
			else
				messages.add(ChatColor.GRAY + "[party promote] " + I18NMessage.getLocale().getString(INOTLEADER));
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
