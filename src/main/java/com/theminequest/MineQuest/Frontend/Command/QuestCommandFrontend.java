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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.I18NMessage;
import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Group.GroupException;
import com.theminequest.MineQuest.API.Group.QuestGroup;
import com.theminequest.MineQuest.API.Group.QuestGroup.QuestStatus;
import com.theminequest.MineQuest.API.Quest.Quest;
import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestDetailsUtils;
import com.theminequest.MineQuest.API.Quest.QuestUtils;
import com.theminequest.MineQuest.API.Tracker.LogStatus;
import com.theminequest.MineQuest.API.Tracker.QuestStatisticUtils;
import com.theminequest.MineQuest.API.Tracker.QuestStatisticUtils.QSException;
import com.theminequest.MineQuest.API.Utils.ChatUtils;

public class QuestCommandFrontend extends CommandFrontend {
	
	public QuestCommandFrontend(){
		super("quest");
	}
	
	public void reload(Player p, String[] args) {
		if (args.length>1){
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getValue());
			return;
		}
		if (args.length==0)
			Managers.getQuestManager().reloadQuests();
		else
			Managers.getQuestManager().reloadQuest(args[0]);
		p.sendMessage(I18NMessage.Cmd_Quest_RELOAD.getValue());
	}

	public void admindrop(Player p, String[] args) {
		if (args.length!=3) {
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getValue());
			return;
		}
		String username = args[0];
		String questname = args[1];
		boolean complete = Boolean.parseBoolean(args[2]);
		if (complete) {
			try {
				QuestStatisticUtils.completeQuest(username, questname);
				QuestDetails d = Managers.getQuestManager().getDetails(args[0]);
				if (d==null || d.getProperty(QuestDetails.QUEST_COMPLETE)==null)
					p.sendMessage(I18NMessage.Quest_COMPLETE + "");
				else
					p.sendMessage(ChatUtils.chatify((String) d.getProperty(QuestDetails.QUEST_COMPLETE)));
			} catch (QSException e) {
				p.sendMessage(I18NMessage.Cmd_Quest_NOTHAVEQUEST.getValue());
			}
		} else {
			try {
				QuestStatisticUtils.dropQuest(username, questname);
				QuestDetails d = Managers.getQuestManager().getDetails(args[0]);
				if (d==null || d.getProperty(QuestDetails.QUEST_ABORT)==null)
					p.sendMessage(I18NMessage.Cmd_Quest_DROP + "");
				else
					p.sendMessage(ChatUtils.chatify((String) d.getProperty(QuestDetails.QUEST_ABORT)));
			} catch (QSException e) {
				p.sendMessage(I18NMessage.Cmd_Quest_NOTHAVEQUEST.getValue());
			}
		}
	}

	public void admininfo(Player p, String[] args){
		if (args.length!=1){
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getValue());
			return;
		}
		QuestDetails qd = Managers.getQuestManager().getDetails(args[0]);
		if (qd==null){
			p.sendMessage(I18NMessage.Cmd_NOSUCHQUEST.getValue());
			return;
		}
		p.sendMessage(QuestDetailsUtils.getOverviewString(qd).split(QuestDetailsUtils.CODE_NEWLINE_SEQ));
	}
	
	public void userhas(Player sender, String[] args) {
		if (args.length!=1){
			sender.sendMessage(I18NMessage.Cmd_INVALIDARGS.getValue());
			return;
		}
		
		Player p = Bukkit.getPlayer(args[0]);
		
		/* Given Quests */
		Map<String, Date> givenquests = QuestStatisticUtils.getQuests(p.getName(),LogStatus.GIVEN);
		
		List<String> givenmessage = new ArrayList<String>();
		givenmessage.add(ChatUtils.formatHeader(I18NMessage.Cmd_Quest_GIVEN.getValue()));
		for (String q : givenquests.keySet()){
			if (q.isEmpty())
				continue;
			QuestDetails qd = Managers.getQuestManager().getDetails(q);
			if (qd!=null){
				givenmessage.add(ChatColor.AQUA + q + " : " + ChatColor.GOLD + qd.getProperty(QuestDetails.QUEST_DISPLAYNAME));
			} else {
				givenmessage.add(ChatColor.GRAY + q + " : <?>");
			}
		}
		
		for (String m : givenmessage)
			sender.sendMessage(m);
		if (givenmessage.size()==1)
			sender.sendMessage(I18NMessage.Cmd_NOQUESTS.getValue());
		
		/* Main World Quests */
		Map<String, Date> mqquests = QuestStatisticUtils.getQuests(p.getName(), LogStatus.ACTIVE);
		List<String> mwmessage = new ArrayList<String>();
		mwmessage.add(ChatUtils.formatHeader(I18NMessage.Cmd_Quest_MAIN.getValue()));
		for (String q : mqquests.keySet()){
			if (q.isEmpty())
				continue;
			Quest quest = Managers.getQuestManager().getMainWorldQuest(p.getName(), q);
			mwmessage.add(ChatColor.LIGHT_PURPLE + q + " : " + ChatColor.GOLD + quest.getDetails().getProperty(QuestDetails.QUEST_NAME));
		}
		for (String m : mwmessage)
			sender.sendMessage(m);
		if (mwmessage.size()==1)
			sender.sendMessage(I18NMessage.Cmd_NOQUESTS.getValue());
	}

	public Boolean drop(Player p, String[] args) {
		if (args.length!=1){
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getValue());
			return false;
		}
		try {
			QuestStatisticUtils.dropQuest(p.getName(), args[0]);
			QuestDetails d = Managers.getQuestManager().getDetails(args[0]);
			if (d==null || d.getProperty(QuestDetails.QUEST_ABORT)==null)
				p.sendMessage(I18NMessage.Cmd_Quest_DROP + "");
			else
				p.sendMessage(ChatUtils.chatify((String) d.getProperty(QuestDetails.QUEST_ABORT)));
		} catch (QSException e) {
			p.sendMessage(I18NMessage.Cmd_Quest_NOTHAVEQUEST.getValue());
			return false;
		}
		return true;
	}

	public void info(Player p, String[] args) {
		if (args.length!=1){
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getValue());
			return;
		}
		
		LogStatus infoQuest = QuestStatisticUtils.hasQuest(p.getName(), args[0]);
		switch (infoQuest) {
		case ACTIVE:
			Quest q = Managers.getQuestManager().getMainWorldQuest(p.getName(),args[0]);
			if (q == null)
				p.sendMessage(I18NMessage.Cmd_Quest_UNAVAILABLE.getValue());
			else
				p.sendMessage(QuestUtils.getStatusString(q).split(QuestDetailsUtils.CODE_NEWLINE_SEQ));
			break;
		case GIVEN:
			QuestDetails qd = Managers.getQuestManager().getDetails(args[0]);
			if (qd==null)
				p.sendMessage(I18NMessage.Cmd_Quest_UNAVAILABLE.getValue());
			else
				p.sendMessage(QuestDetailsUtils.getOverviewString(qd).split(QuestDetailsUtils.CODE_NEWLINE_SEQ));
			break;
		default:
			p.sendMessage(I18NMessage.Cmd_NOSUCHQUEST.getValue());
			break;
		}
		

	}
	
	public Boolean abandon(Player p, String[] args) {
		if (args.length!=0){
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getValue());
			return false;
		}
		if (Managers.getQuestGroupManager().indexOf(p)==-1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOPARTY.getValue());
			return false;
		}
		QuestGroup g = Managers.getQuestGroupManager().get(p);
		if (!g.getLeader().equals(p)){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOTLEADER.getValue());
			return false;
		}
		if (g.getQuest()==null){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_NOACTIVE.getValue());
			return false;
		}
		if (g.getQuest().isFinished()!=null){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_ALREADYDONE.getValue());
			return false;
		}
		try {
			g.abandonQuest();
			return true;
		} catch (GroupException e) {
			e.printStackTrace();
			p.sendMessage(ChatColor.GRAY + "ERR: " + e.getMessage());
			return false;
		}
	}
	
	public Boolean active(Player p, String[] args) {
		if (args.length!=0){
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getValue());
			return false;
		}
		if (Managers.getQuestGroupManager().indexOf(p)==-1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOPARTY.getValue());
			return false;
		}
		QuestGroup g = Managers.getQuestGroupManager().get(p);
		if (g.getQuest()==null){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_NOACTIVE.getValue());
			return false;
		}
		p.sendMessage(QuestUtils.getStatusString(g.getQuest()).split(QuestDetailsUtils.CODE_NEWLINE_SEQ));
		return true;
	}
		
	public Boolean exit(Player p, String[] args) {
		if (args.length!=0){
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getValue());
			return false;
		}
		if (Managers.getQuestGroupManager().indexOf(p)==-1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOPARTY.getValue());
			return false;
		}
		QuestGroup g = Managers.getQuestGroupManager().get(p);
		if (g.getQuest()==null){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_NOACTIVE.getValue());
			return false;
		}
		if (!g.getQuest().isInstanced()){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_MAINWORLD.getValue());
			return false;
		}
		if (!g.getLeader().equals(p)){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOTLEADER.getValue());
			return false;
		}
		if (g.getQuestStatus()!=QuestStatus.INQUEST){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_NOTINQUEST.getValue());
			return false;
		}
		if (g.getQuest().isFinished()==null){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_EXITUNFINISHED.getValue());
			return false;
		}
		try {
			g.exitQuest();
			return true;
		} catch (GroupException e) {
			e.printStackTrace();
			p.sendMessage(ChatColor.GRAY + "ERR: " + e.getMessage());
			return false;
		}
	}
	
	public Boolean start(final Player p, final String[] args) {
		if (args.length!=1){
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getValue());
			return false;
		}
		
		Map<String, Date> quests = QuestStatisticUtils.getQuests(p.getName(), LogStatus.GIVEN);
		
		if (!quests.containsKey(args[0])){
			p.sendMessage(I18NMessage.Cmd_Quest_NOTHAVEQUEST.getValue());
			return false;
		}
		
		final QuestDetails qd = Managers.getQuestManager().getDetails(args[0]);
		if (qd==null){
			p.sendMessage(I18NMessage.Cmd_Quest_UNAVAILABLE.getValue());
			return false;
		}
		
		if (Managers.getQuestGroupManager().indexOf(p)==-1){
			Managers.getQuestGroupManager().createNewGroup(p);
			p.sendMessage(ChatColor.YELLOW + I18NMessage.Cmd_Party_CREATE.getValue());
		}
		final QuestGroup g = Managers.getQuestGroupManager().get(p);
		if (!g.getLeader().equals(p)){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOTLEADER.getValue());
			return false;
		}
		if (g.getQuest()!=null){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_ALREADYACTIVE.getValue());
			return false;
		}
		new Thread(new Runnable(){
			
			@Override
			public void run() {
				p.sendMessage(ChatColor.YELLOW + "[Quest] Starting up. May take a few minutes.");
				try {
					g.startQuest(qd);
					g.enterQuest();
				} catch (GroupException e) {
					e.printStackTrace();
					p.sendMessage(ChatColor.RED + "[Quest] Couldn't start your quest. :C");
					return;
				}
				p.sendMessage(ChatColor.YELLOW + "[Quest] Quest has been started!");
			}
			
		}).start();
		
		return true;
	}
	
	
	public void help(CommandSender sender, String[] args) {
		
		Player p = (Player)sender;
		
		List<String> messages = new LinkedList<String>();
		
		/*
		 * OP: reload [name]
		 * OP: admindrop <username> <name> <true/false (complete?)>
		 * OP: admininfo [name]
		 * OP: userhas <username>
		 * NoByDefault: drop <name>
		 *
		 * info [name] // when called by non-admin, it checks if the player has the actual quest
		 *
		 * abandon
		 * active // in instance only : shows instance status.
		 * exit
		 * start <name>
		 */
		
		messages.add(ChatUtils.formatHeader(I18NMessage.Cmd_Quest_HELP.getValue()));
		
		// admin
		if (p.hasPermission("minequest.command.quest.reload"))
			messages.add(ChatUtils.formatHelp("quest reload [name]", "Reload quest into memory (or all)"));
		if (p.hasPermission("minequest.command.quest.admindrop"))
			messages.add(ChatUtils.formatHelp("quest admindrop <user> <name> <true/false>", "Drop a quest for a user completed or discarded."));
		if (p.hasPermission("minequest.command.quest.admininfo"))
			messages.add(ChatUtils.formatHelp("quest admininfo <name>", I18NMessage.Cmd_Quest_HELPINFO.getValue()));
		if (p.hasPermission("minequest.command.quest.userhas"))
			messages.add(ChatUtils.formatHelp("quest userhas <user>", "See what quests a user has."));
		
		// off by default
		if (p.hasPermission("minequest.command.quest.drop"))
			messages.add(ChatUtils.formatHelp("quest drop <name>", I18NMessage.Cmd_Quest_HELPDROP.getValue()));
				
		// basic commands
		boolean inGroup = Managers.getQuestGroupManager().get(p) != null;
		boolean isLeader = inGroup && Managers.getQuestGroupManager().get(p).getLeader().equals(p);
		QuestStatus hasQuest = inGroup ? Managers.getQuestGroupManager().get(p).getQuestStatus() : QuestStatus.NOQUEST;
		CompleteStatus questStatus = hasQuest == QuestStatus.INQUEST ? Managers.getQuestGroupManager().get(p).getQuest().isFinished() : CompleteStatus.IGNORE;
		
		switch (hasQuest) {
		case INQUEST:
			messages.add(ChatUtils.formatHelp("quest active", "Get information about this active quest."));
			if (isLeader) {
				if (questStatus == null)
					messages.add(ChatUtils.formatHelp("quest abandon", I18NMessage.Cmd_Quest_HELPABANDON.getValue()));
				else
					messages.add(ChatUtils.formatHelp("quest exit", I18NMessage.Cmd_Quest_HELPEXIT.getValue()));
			}
			break;
		case MAINWORLDQUEST:
			break;
		case NOQUEST:
		case NOTINQUEST:
			if (p.hasPermission("minequest.command.quest.info"))
				messages.add(ChatUtils.formatHelp("quest info [name]", "Get information about a quest you have."));
			if (isLeader)
				messages.add(ChatUtils.formatHelp("quest start [name]", I18NMessage.Cmd_Quest_HELPSTART.getValue()));
			else if (!inGroup)
				messages.add(ChatUtils.formatHelp("quest start [name]", I18NMessage.Cmd_Quest_HELPSTARTNOPARTY.getValue()));
			break;
		default:
			break;
		}
				
		for (String m : messages) {
			p.sendMessage(m);
		}
	}
	
	@Override
	public boolean allowConsole() {
		return false;
	}
	
	@Override
	public void noOptionSpecified(CommandSender sender, String[] args) {
		Player p = (Player)sender;
		
		/* Given Quests */
		Map<String, Date> givenquests = QuestStatisticUtils.getQuests(p.getName(),LogStatus.GIVEN);
		
		List<String> givenmessage = new ArrayList<String>();
		givenmessage.add(ChatUtils.formatHeader(I18NMessage.Cmd_Quest_GIVEN.getValue()));
		for (String q : givenquests.keySet()){
			if (q.isEmpty())
				continue;
			QuestDetails qd = Managers.getQuestManager().getDetails(q);
			if (qd!=null){
				givenmessage.add(ChatColor.AQUA + q + " : " + ChatColor.GOLD + qd.getProperty(QuestDetails.QUEST_DISPLAYNAME));
			} else {
				givenmessage.add(ChatColor.GRAY + q + " : <?>");
			}
		}
		
		for (String m : givenmessage)
			p.sendMessage(m);
		if (givenmessage.size()==1)
			p.sendMessage(I18NMessage.Cmd_NOQUESTS.getValue());
		
		/* Main World Quests */
		Map<String, Date> mqquests = QuestStatisticUtils.getQuests(p.getName(), LogStatus.ACTIVE);
		List<String> mwmessage = new ArrayList<String>();
		mwmessage.add(ChatUtils.formatHeader(I18NMessage.Cmd_Quest_MAIN.getValue()));
		for (String q : mqquests.keySet()){
			if (q.isEmpty())
				continue;
			Quest quest = Managers.getQuestManager().getMainWorldQuest(p.getName(), q);
			mwmessage.add(ChatColor.LIGHT_PURPLE + q + " : " + ChatColor.GOLD + quest.getDetails().getProperty(QuestDetails.QUEST_NAME));
		}
		for (String m : mwmessage)
			p.sendMessage(m);
		if (mwmessage.size()==1)
			p.sendMessage(I18NMessage.Cmd_NOQUESTS.getValue());
		
		p.sendMessage(ChatUtils.formatHeader("Help Options: /quest help"));
	}
	
}
