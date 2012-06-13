package com.theminequest.MineQuest.Frontend.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.I18NMessage;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Group.GroupException;
import com.theminequest.MineQuest.API.Group.QuestGroup;
import com.theminequest.MineQuest.API.Group.QuestGroup.QuestStatus;
import com.theminequest.MineQuest.API.Quest.Quest;
import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestDetailsUtils;
import com.theminequest.MineQuest.API.Tracker.QuestStatisticUtils;
import com.theminequest.MineQuest.API.Tracker.QuestStatisticUtils.QSException;
import com.theminequest.MineQuest.API.Tracker.QuestStatisticUtils.Status;
import com.theminequest.MineQuest.API.Utils.ChatUtils;

public class QuestCommandFrontend extends CommandFrontend {

	public QuestCommandFrontend(){
		super("quest");
	}

	public Boolean given(Player p, String[] args) {
		if (args.length!=0){
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getDescription());
			return false;
		}
		String[] quests = QuestStatisticUtils.getQuests(p,Status.GIVEN);

		List<String> message = new ArrayList<String>();
		message.add(ChatUtils.formatHeader(I18NMessage.Cmd_Quest_ACCEPTED.getDescription()));
		for (String q : quests){
			QuestDetails qd = Managers.getQuestManager().getDetails(q);
			if (qd!=null)
				message.add(ChatColor.AQUA + q + " : " + ChatColor.GOLD + qd.getProperty(QuestDetails.QUEST_NAME));
			else
				message.add(ChatColor.AQUA + q + " : " + ChatColor.GRAY + "<unavailable>");
		}

		for (String m : message)
			p.sendMessage(m);
		return true;
	}

	public Boolean drop(Player p, String[] args) {
		if (args.length!=1){
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getDescription());
			return false;
		}
		try {
			QuestStatisticUtils.degiveQuest(p, args[0]);
		} catch (QSException e) {
			p.sendMessage(I18NMessage.Cmd_Quest_NOTHAVEQUEST.getDescription());
			return false;
		}
		return true;
	}

	public Boolean abandon(Player p, String[] args) {
		if (args.length!=0){
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getDescription());
			return false;
		}
		if (Managers.getQuestGroupManager().indexOf(p)==-1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOPARTY.getDescription());
			return false;
		}
		QuestGroup g = Managers.getQuestGroupManager().get(p);
		if (!g.getLeader().equals(p)){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOTLEADER.getDescription());
			return false;
		}
		if (g.getQuest()==null){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_NOACTIVE.getDescription());
			return false;
		}
		if (g.getQuest().isFinished()!=null){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_ALREADYDONE.getDescription());
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
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getDescription());
			return false;
		}
		if (Managers.getQuestGroupManager().indexOf(p)==-1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOPARTY.getDescription());
			return false;
		}
		QuestGroup g = Managers.getQuestGroupManager().get(p);
		if (g.getQuest()==null){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_NOACTIVE.getDescription());
			return false;
		}
		p.sendMessage(g.getQuest().toString().split("\n"));
		return true;
	}

	public Boolean enter(Player p, String[] args) {
		if (args.length!=0){
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getDescription());
			return false;
		}
		if (Managers.getQuestGroupManager().indexOf(p)==-1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOPARTY.getDescription());
			return false;
		}
		QuestGroup g = Managers.getQuestGroupManager().get(p);
		if (g.getQuest()==null){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_NOACTIVE.getDescription());
			return false;
		}
		if (!g.getQuest().isInstanced()){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_MAINWORLD.getDescription());
			return false;
		}
		if (!g.getLeader().equals(p)){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOTLEADER.getDescription());
			return false;
		}
		if (g.getQuestStatus()==QuestStatus.INQUEST){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_INQUEST.getDescription());
			return false;
		}
		try {
			g.enterQuest();
			return true;
		} catch (GroupException e) {
			e.printStackTrace();
			p.sendMessage(ChatColor.GRAY + "ERR: " + e.getMessage());
			return false;
		}
	}

	public Boolean exit(Player p, String[] args) {
		if (args.length!=0){
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getDescription());
			return false;
		}
		if (Managers.getQuestGroupManager().indexOf(p)==-1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOPARTY.getDescription());
			return false;
		}
		QuestGroup g = Managers.getQuestGroupManager().get(p);
		if (g.getQuest()==null){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_NOACTIVE.getDescription());
			return false;
		}
		if (!g.getQuest().isInstanced()){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_MAINWORLD.getDescription());
			return false;
		}
		if (!g.getLeader().equals(p)){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOTLEADER.getDescription());
			return false;
		}
		if (g.getQuestStatus()!=QuestStatus.INQUEST){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_NOTINQUEST.getDescription());
			return false;
		}
		if (g.getQuest().isFinished()==null){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_EXITUNFINISHED.getDescription());
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

	public Boolean info(Player p, String[] args){
		if (args.length!=1){
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getDescription());
			return false;
		}
		QuestDetails qd = Managers.getQuestManager().getDetails(args[0]);
		if (qd==null){
			p.sendMessage(I18NMessage.Cmd_NOSUCHQUEST.getDescription());
			return false;
		}
		p.sendMessage(QuestDetailsUtils.getOverviewString(qd).split("\n"));
		return true;
	}
	
	public Boolean reload(Player p, String[] args) {
		if (args.length>1){
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getDescription());
			return false;
		}
		if (!p.isOp()){
			p.sendMessage(I18NMessage.Cmd_NOTOP.getDescription());
			return false;
		}
		if (args.length==0)
			Managers.getQuestManager().reloadQuests();
		else
			Managers.getQuestManager().reloadQuest(args[0]);
		return true;
	}

	public Boolean start(final Player p, final String[] args) {
		if (args.length!=1){
			p.sendMessage(I18NMessage.Cmd_INVALIDARGS.getDescription());
			return false;
		}
		if (Managers.getQuestGroupManager().indexOf(p)==-1){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOPARTY.getDescription());
			return false;
		}
		final QuestGroup g = Managers.getQuestGroupManager().get(p);
		if (!g.getLeader().equals(p)){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOTLEADER.getDescription());
			return false;
		}
		if (g.getQuest()!=null){
			p.sendMessage(ChatColor.RED + I18NMessage.Cmd_Quest_ALREADYACTIVE.getDescription());
			return false;
		}

		List<String> quests = Arrays.asList(QuestStatisticUtils.getQuests(p, Status.GIVEN));

		if (!quests.contains(args[0])){
			p.sendMessage(I18NMessage.Cmd_Quest_NOTHAVEQUEST.getDescription());
			return false;
		}
		
		final QuestDetails qd = Managers.getQuestManager().getDetails(args[0]);
		if (qd==null){
			p.sendMessage(I18NMessage.Cmd_Quest_UNAVAILABLE.getDescription());
			return false;
		}
		
		// TEST new multithreading
		new Thread(new Runnable(){

			@Override
			public void run() {
				p.sendMessage(ChatColor.YELLOW + "[Quest] Starting up. May take a few minutes.");
				try {
					g.startQuest(qd);
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


	public Boolean help(Player p, String[] args) {

		List<String> messages = new ArrayList<String>();
		boolean inGroup = false;
		boolean isLeader = false;
		QuestStatus inQuest = QuestStatus.NOQUEST;
		Quest active = null;
		if (Managers.getQuestGroupManager().indexOf(p)!=-1){
			QuestGroup g = Managers.getQuestGroupManager().get(p);
			inGroup = true;
			isLeader = g.getLeader().equals(p);
			inQuest = g.getQuestStatus();
			active = g.getQuest();
		}

		/*
		 * OP: reload <name>
		 * given
		 * info
		 * 
		 * abandon
		 * active
		 * enter
		 * exit
		 * start <name>
		 */
		if (p.isOp())
			messages.add(ChatUtils.formatHelp("quest reload [name]", "Reload quest into memory (or all)"));
		messages.add(ChatUtils.formatHeader(I18NMessage.Cmd_Quest_HELP.getDescription()));
		messages.add(ChatUtils.formatHelp("quest given", I18NMessage.Cmd_Quest_HELPGIVEN.getDescription()));
		messages.add(ChatUtils.formatHelp("quest drop <name>", I18NMessage.Cmd_Quest_HELPDROP.getDescription()));
		messages.add(ChatUtils.formatHelp("quest info <name>", I18NMessage.Cmd_Quest_HELPINFO.getDescription()));

		if (inGroup){
			if (active != null && isLeader && active.isFinished()==null)
				messages.add(ChatUtils.formatHelp("quest abandon", I18NMessage.Cmd_Quest_HELPABANDON.getDescription()));
			else if (active != null && isLeader && active.isFinished()!=null)
				messages.add(ChatColor.GRAY + "[quest abandon] " + I18NMessage.Cmd_Quest_ALREADYDONE.getDescription());
			else if (active != null)
				messages.add(ChatColor.GRAY + "[quest abandon] " + I18NMessage.Cmd_NOTLEADER.getDescription());
			else
				messages.add(ChatColor.GRAY + "[quest abandon] " + I18NMessage.Cmd_Quest_NOACTIVE.getDescription());
			if (active!=null)
				messages.add(ChatUtils.formatHelp("quest active", I18NMessage.Cmd_Quest_HELPACTIVE.getDescription()));
			else
				messages.add(ChatColor.GRAY + "[quest active] " + I18NMessage.Cmd_Quest_NOACTIVE.getDescription());
			if (active!=null && inQuest==QuestStatus.NOTINQUEST && isLeader && active.isInstanced())
				messages.add(ChatUtils.formatHelp("quest enter", I18NMessage.Cmd_Quest_HELPENTER.getDescription()));
			else if (active!=null && inQuest==QuestStatus.INQUEST && isLeader && active.isInstanced() && active.isFinished()!=null )
				messages.add(ChatUtils.formatHelp("quest exit", I18NMessage.Cmd_Quest_HELPEXIT.getDescription()));
			else if (active!=null && inQuest==QuestStatus.INQUEST && isLeader && active.isInstanced() )
				messages.add(ChatColor.GRAY + "[quest exit] " + I18NMessage.Cmd_Quest_EXITUNFINISHED.getDescription());
			else if (active!=null && !active.isInstanced())
				messages.add(ChatColor.GRAY + "[quest enter/exit] " + I18NMessage.Cmd_Quest_MAINWORLD.getDescription());
			else if (active!=null && !isLeader)
				messages.add(ChatColor.GRAY + "[quest enter/exit] " + I18NMessage.Cmd_NOTLEADER.getDescription());
			else
				messages.add(ChatColor.GRAY + "[quest enter/exit] " + I18NMessage.Cmd_Quest_NOACTIVE.getDescription());
			if (active == null && isLeader)
				messages.add(ChatUtils.formatHelp("quest start <name>", I18NMessage.Cmd_Quest_HELPSTART.getDescription()));
			else if (active == null)
				messages.add(ChatColor.GRAY + "[quest start] " + I18NMessage.Cmd_NOTLEADER.getDescription());
			else
				messages.add(ChatColor.GRAY + "[quest start] " + I18NMessage.Cmd_Quest_ALREADYACTIVE.getDescription());
		} else {
			messages.add(ChatUtils.formatHelp("quest start <name>", I18NMessage.Cmd_Quest_HELPSTARTNOPARTY.getDescription()));
			messages.add(ChatColor.AQUA + I18NMessage.Cmd_Quest_JOINPARTY.getDescription());
		}

		for (String m : messages) {
			p.sendMessage(m);
		}

		return true;
	}

	@Override
	public boolean allowConsole() {
		return false;
	}

}
