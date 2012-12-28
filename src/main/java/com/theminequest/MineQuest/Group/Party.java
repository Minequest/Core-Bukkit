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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.BukkitEvents.GroupPlayerJoinedEvent;
import com.theminequest.MineQuest.API.BukkitEvents.GroupPlayerQuitEvent;
import com.theminequest.MineQuest.API.Group.Group;
import com.theminequest.MineQuest.API.Group.GroupException;
import com.theminequest.MineQuest.API.Group.GroupException.GroupReason;
import com.theminequest.MineQuest.API.Group.QuestGroup;
import com.theminequest.MineQuest.API.Quest.Quest;
import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestDetailsUtils;
import com.theminequest.MineQuest.API.Quest.QuestUtils;

public class Party implements QuestGroup {
	
	public static final String CONFIG_CAPACITY = "party.maxCapacity";
	
	private long teamid;
	private List<Player> players;
	private LinkedHashMap<Player, Location> locations;
	private int capacity;
	private Quest quest;
	private QuestStatus status;
	private boolean pvp;
	
	protected Party(long id, List<Player> p) {
		if (p.size() <= 0)
			throw new IllegalArgumentException(GroupReason.BADCAPACITY.name());
		// ^ never should encounter this unless a third-party tries to, in which
		// case they get what they deserve.
		teamid = id;
		players = Collections.synchronizedList(p);
		locations = null;
		quest = null;
		status = QuestStatus.NOQUEST;
		capacity = MineQuest.configuration.groupConfig.getInt(Party.CONFIG_CAPACITY, 8);
	}
	
	@Override
	public synchronized Player getLeader() {
		return players.get(0);
	}
	
	@Override
	public synchronized void setLeader(Player p) throws GroupException {
		if (!contains(p))
			throw new GroupException(GroupReason.NOTONTEAM);
		players.remove(p);
		players.add(0, p);
	}
	
	@Override
	public synchronized List<Player> getMembers() {
		return players;
	}
	
	@Override
	public synchronized void setCapacity(int c) throws GroupException {
		if (c < players.size())
			throw new GroupException(GroupReason.BADCAPACITY);
		capacity = c;
	}
	
	@Override
	public synchronized int getCapacity() {
		return capacity;
	}
	
	@Override
	public long getID() {
		return teamid;
	}
	
	@Override
	public synchronized boolean contains(Player p) {
		return players.contains(p);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theminequest.MineQuest.Group.Group#startQuest(com.theminequest.MineQuest
	 * .Quest.Quest)
	 * let the backend handle checking valid quests, calling QuestManager,
	 * etc...
	 */
	@Override
	public synchronized void startQuest(QuestDetails d) throws GroupException {
		if (quest != null)
			throw new GroupException(GroupReason.ALREADYONQUEST);
		// check requirements
		if (!QuestDetailsUtils.startRequirementsMet(d, getLeader()))
			throw new GroupException(GroupReason.REQUIREMENTSNOTFULFILLED);
		quest = Managers.getQuestManager().startQuest(d, getLeader().getName());
		
		status = QuestStatus.NOTINQUEST;
		boolean loadworld = quest.getDetails().getProperty(QuestDetails.QUEST_LOADWORLD);
		if (!loadworld) {
			quest.startQuest();
			status = QuestStatus.MAINWORLDQUEST;
		}
	}
	
	@Override
	public synchronized void abandonQuest() throws GroupException {
		if (quest == null)
			throw new GroupException(GroupReason.NOQUEST);
		quest.finishQuest(CompleteStatus.CANCELED);
		if (status == QuestStatus.INQUEST)
			exitQuest();
		Quest q = quest;
		status = QuestStatus.NOQUEST;
		quest = null;
		if (q != null)
			q.cleanupQuest();
		
	}
	
	/**
	 * Get the quest the team is undertaking.
	 * 
	 * @return Quest the team is undertaking, or <code>null</code> if the team
	 *         is not on a quest.
	 */
	@Override
	public synchronized Quest getQuest() {
		return quest;
	}
	
	@Override
	public synchronized void teleportPlayers(Location l) {
		for (Player p : players)
			p.teleport(l);
	}
	
	@Override
	public synchronized void add(Player p) throws GroupException {
		if (Managers.getGroupManager().indexOf(p) != -1)
			throw new GroupException(GroupReason.ALREADYINTEAM);
		if (players.size() >= capacity)
			throw new GroupException(GroupReason.BADCAPACITY);
		if (contains(p))
			throw new GroupException(GroupReason.ALREADYINTEAM);
		if (status == QuestStatus.INQUEST)
			throw new GroupException(GroupReason.INSIDEQUEST);
		// MineQuest.playerManager.getPlayerDetails(p).setTeam(teamid);
		GroupPlayerJoinedEvent e = new GroupPlayerJoinedEvent(this, p);
		Bukkit.getPluginManager().callEvent(e);
		if (e.isCancelled())
			throw new GroupException(GroupReason.EXTERNALEXCEPTION);
		players.add(p);
	}
	
	@Override
	public synchronized void remove(Player p) throws GroupException {
		if (!contains(p))
			throw new GroupException(GroupReason.NOTONTEAM);
		GroupPlayerQuitEvent e = new GroupPlayerQuitEvent(this, p);
		Bukkit.getPluginManager().callEvent(e);
		if (e.isCancelled())
			if (e.getPlayer().isOnline())
				throw new GroupException(GroupReason.EXTERNALEXCEPTION);
		// MineQuest.playerManager.getPlayerDetails(p).setTeam(-1);
		players.remove(p);
		if (locations != null) {
			moveBackToLocations(p);
			locations.remove(p);
		}
		
		if (players.size() <= 0) {
			if (quest != null)
				abandonQuest();
			Managers.getGroupManager().disposeGroup(this);
		}
	}
	
	@Override
	public synchronized void enterQuest() throws GroupException {
		if (quest == null)
			throw new GroupException(GroupReason.NOQUEST);
		if (status == QuestStatus.INQUEST)
			throw new GroupException(GroupReason.INSIDEQUEST);
		if (!quest.isInstanced())
			throw new GroupException(GroupReason.MAINWORLDQUEST);
		recordCurrentLocations();
		status = QuestStatus.INQUEST;
		teleportPlayers(QuestUtils.getSpawnLocation(quest));
		quest.startQuest();
	}
	
	public synchronized void recordCurrentLocations() {
		locations = new LinkedHashMap<Player, Location>();
		for (Player p : players)
			locations.put(p, p.getLocation());
	}
	
	public synchronized void moveBackToLocations() throws GroupException {
		for (Player p : players)
			moveBackToLocations(p);
		locations = null;
	}
	
	public synchronized void moveBackToLocations(Player p) throws GroupException {
		if (locations == null)
			throw new GroupException(GroupReason.NOLOCATIONS);
		p.teleport(locations.get(p));
		locations.remove(p);
	}
	
	@Override
	public synchronized void exitQuest() throws GroupException {
		if (quest == null)
			throw new GroupException(GroupReason.NOQUEST);
		if (status != QuestStatus.INQUEST)
			throw new GroupException(GroupReason.NOTINSIDEQUEST);
		if (!quest.isInstanced())
			throw new GroupException(GroupReason.MAINWORLDQUEST);
		if (quest.isFinished() == null)
			throw new GroupException(GroupReason.UNFINISHEDQUEST);
		moveBackToLocations();
		Quest q = quest;
		status = QuestStatus.NOQUEST;
		quest = null;
		q.cleanupQuest();
	}
	
	@Override
	public synchronized void finishQuest() throws GroupException {
		if (quest == null)
			throw new GroupException(GroupReason.NOQUEST);
		if (quest.isInstanced())
			throw new GroupException(GroupReason.NOTMAINWORLDQUEST);
		if (quest.isFinished() == null)
			throw new GroupException(GroupReason.UNFINISHEDQUEST);
		Quest q = quest;
		quest = null;
		status = QuestStatus.NOQUEST;
		q.cleanupQuest();
	}
	
	@Override
	public int compareTo(Group arg0) {
		return (int) (teamid - arg0.getID());
	}
	
	@Override
	public boolean isPVP() {
		return pvp;
	}
	
	@Override
	public void setPVP(boolean on) {
		pvp = on;
	}
	
	@Override
	public QuestStatus getQuestStatus() {
		return status;
	}
	
}
