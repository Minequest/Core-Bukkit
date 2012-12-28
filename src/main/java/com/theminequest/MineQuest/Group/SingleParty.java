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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.theminequest.MineQuest.Group;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.Group.Group;
import com.theminequest.MineQuest.API.Group.GroupException;
import com.theminequest.MineQuest.API.Group.GroupException.GroupReason;
import com.theminequest.MineQuest.API.Group.QuestGroup;
import com.theminequest.MineQuest.API.Quest.Quest;
import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Tracker.QuestStatisticUtils;
import com.theminequest.MineQuest.API.Tracker.QuestStatisticUtils.QSException;

public class SingleParty implements QuestGroup {
	
	private Player player;
	private Quest activeQuest;
	
	public SingleParty(Player player, Quest activeQuest) {
		this.player = player;
		this.activeQuest = activeQuest;
	}
	
	@Override
	public long getID() {
		return -1;
	}
	
	@Override
	public Player getLeader() {
		return player;
	}
	
	@Override
	public void setLeader(Player p) throws GroupException {
		throw new GroupException(GroupReason.MAINWORLDQUEST);
	}
	
	@Override
	public List<Player> getMembers() {
		LinkedList<Player> tr = new LinkedList<Player>();
		tr.addFirst(player);
		return tr;
	}
	
	@Override
	public void add(Player p) throws GroupException {
		throw new GroupException(GroupReason.MAINWORLDQUEST);
	}
	
	@Override
	public void remove(Player p) throws GroupException {
		throw new GroupException(GroupReason.MAINWORLDQUEST);
	}
	
	@Override
	public boolean contains(Player p) {
		if (p.equals(player))
			return true;
		return false;
	}
	
	@Override
	public int getCapacity() {
		return 1;
	}
	
	@Override
	public void setCapacity(int capacity) throws GroupException {
		throw new GroupException(GroupReason.MAINWORLDQUEST);
	}
	
	@Override
	public boolean isPVP() {
		return false;
	}
	
	@Override
	public void setPVP(boolean on) {
		// do nothing
	}
	
	@Override
	public void teleportPlayers(Location l) {
		player.teleport(l);
	}
	
	@Override
	public int compareTo(Group arg0) {
		return Integer.valueOf(player.getEntityId()).compareTo(arg0.getLeader().getEntityId());
	}
	
	@Override
	public Quest getQuest() {
		return activeQuest;
	}
	
	@Override
	public QuestStatus getQuestStatus() {
		return QuestStatus.MAINWORLDQUEST;
	}
	
	@Override
	public void startQuest(QuestDetails d) throws GroupException {
		throw new GroupException(GroupReason.MAINWORLDQUEST);
	}
	
	@Override
	public void abandonQuest() throws GroupException {
		throw new GroupException(GroupReason.MAINWORLDQUEST);
	}
	
	@Override
	public void enterQuest() throws GroupException {
		throw new GroupException(GroupReason.MAINWORLDQUEST);
	}
	
	@Override
	public void exitQuest() throws GroupException {
		throw new GroupException(GroupReason.MAINWORLDQUEST);
	}
	
	@Override
	public void finishQuest() throws GroupException {
		try {
			QuestStatisticUtils.completeQuest(player.getName(), (String) activeQuest.getDetails().getProperty(QuestDetails.QUEST_NAME));
		} catch (QSException e) {
			throw new GroupException(e);
		}
	}
	
}
