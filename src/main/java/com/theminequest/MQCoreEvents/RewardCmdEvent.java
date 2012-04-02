package com.theminequest.MQCoreEvents;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.EventsAPI.QEvent;
import com.theminequest.MineQuest.Group.Group;

public class RewardCmdEvent extends QEvent {
	
	private String[] cmds;

	public RewardCmdEvent(long q, int e, String details) {
		super(q, e, details);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.EventsAPI.QEvent#parseDetails(java.lang.String[])
	 * [n] command
	 * * %p in commands are substituted with player name.
	 */
	@Override
	public void parseDetails(String[] details) {
		cmds = details;
	}

	@Override
	public boolean conditions() {
		return true;
	}

	@Override
	public CompleteStatus action() {
		long gid = MineQuest.groupManager.indexOfQuest(MineQuest.questManager.getQuest(getQuestId()));
		Group g = MineQuest.groupManager.getGroup(gid);
		for (Player p : g.getPlayers()){
			for (String s : cmds){
				String mod = s.replaceAll("%p", p.getName());
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), mod);
			}
		}
		return CompleteStatus.SUCCESS;
	}

}
