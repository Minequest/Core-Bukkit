package com.theminequest.MQCoreEvents;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Events.QuestEvent;
import com.theminequest.MineQuest.API.Group.Group;
import com.theminequest.MineQuest.API.Group.QuestGroup;

public class RewardCmdEvent extends QuestEvent {
	
	private String[] cmds;

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
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
		QuestGroup g = Managers.getQuestGroupManager().get(getQuest());
		for (Player p : g.getMembers()){
			for (String s : cmds){
				String mod = s.replaceAll("%p", p.getName());
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), mod);
			}
		}
		return CompleteStatus.SUCCESS;
	}

	@Override
	public Integer switchTask() {
		return null;
	}

}
