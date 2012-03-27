/**
 * This file, Team.java, is part of MineQuest:
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
package com.theminequest.MineQuest.Group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.BukkitEvents.QuestStartedEvent;
import com.theminequest.MineQuest.Group.GroupExceptionEvent.Cause;
import com.theminequest.MineQuest.Player.PlayerManager;
import com.theminequest.MineQuest.Quest.Quest;

public class Team implements Group {


	private long teamid;
	private ArrayList<Player> players;
	private int capacity;
	private Quest quest;

	protected Team(long id, ArrayList<Player> p){
		if (p.size()<=0 || p.size()>MineQuest.groupManager.TEAM_MAX_CAPACITY)
			throw new IllegalArgumentException("Invalid team size!");
		teamid = id;
		players = (ArrayList<Player>) Collections.synchronizedList(p);
		quest = null;
		capacity = MineQuest.groupManager.TEAM_MAX_CAPACITY;
	}

	@Override
	public synchronized Player getLeader(){
		return players.get(0);
	}

	@Override
	public synchronized void setLeader(Player p) throws GroupExceptionEvent{
		if (!contains(p))
			throw new GroupExceptionEvent(Cause.NOTONTEAM);
		players.remove(p);
		players.add(0, p);
	}

	@Override
	public synchronized List<Player> getPlayers(){
		return players;
	}
	
	/*
	 * Mark team in a way such that nobody can get on the team anymore.
	 * This should help Java trigger GC on this object.
	 */
	@Override
	public void lockTeam(){
		capacity = 0;
	}

	@Override
	public synchronized void setCapacity(int c) throws GroupExceptionEvent{
		if (c<=0 || c>players.size())
			throw new GroupExceptionEvent(Cause.BADCAPACITY);
		capacity = c;
	}

	@Override
	public synchronized int getCapacity(){
		return capacity;
	}

	@Override
	public long getID(){
		return teamid;
	}

	@Override
	public synchronized boolean contains(Player p){
		return players.contains(p);
	}

	@Override
	public synchronized void startQuest(Quest quest) throws GroupExceptionEvent {
		if (quest!=null)
			throw new GroupExceptionEvent(Cause.ALREADYONQUEST);
		this.quest = quest;
		QuestStartedEvent event = new QuestStartedEvent(quest);
		Bukkit.getPluginManager().callEvent(event);
	}

	@Override
	public synchronized void abandonQuest() throws GroupExceptionEvent {
		if (quest==null)
			throw new GroupExceptionEvent(Cause.NOQUEST);
		quest.finishQuest(CompleteStatus.CANCELED);
		quest = null;
	}
	
	/**
	 * Get the quest the team is undertaking.
	 * @return Quest the team is undertaking, or <code>null</code> if the team
	 * is not on a quest.
	 */
	@Override
	public synchronized Quest getQuest() {
		return quest;
	}

	@Override
	public synchronized void teleportPlayers(Location l) {
		for (Player p : players){
			p.teleport(l);
		}
	}

	@Override
	public synchronized void add(Player p) throws GroupExceptionEvent {
		if (players.size()>=capacity)
			throw new GroupExceptionEvent(Cause.OVERCAPACITY);
		if (contains(p))
			throw new GroupExceptionEvent(Cause.ALREADYINTEAM);
		//MineQuest.playerManager.getPlayerDetails(p).setTeam(teamid);
		players.add(p);
		// TODO add TeamPlayerJoinedEvent
	}

	@Override
	public synchronized void remove(Player p) throws GroupExceptionEvent{
		if (!contains(p))
			throw new GroupExceptionEvent(Cause.NOTONTEAM);
		//MineQuest.playerManager.getPlayerDetails(p).setTeam(-1);
		players.remove(p);
		
		if (players.size()<=0)
			MineQuest.groupManager.removeEmptyTeam(teamid);
	}

}
