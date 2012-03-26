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
package com.theminequest.MineQuest.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.BukkitEvents.QuestStartedEvent;
import com.theminequest.MineQuest.Player.PlayerManager;
import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Team.TeamExceptionEvent.Cause;

public class Team {

	private static final int MAX_CAPACITY = 8;
	private long teamid;
	private ArrayList<Player> players;
	private int capacity;
	private Quest quest;

	protected Team(long id, ArrayList<Player> p){
		if (p.size()<=0 || p.size()>Team.MAX_CAPACITY)
			throw new IllegalArgumentException("Invalid team size!");
		teamid = id;
		players = (ArrayList<Player>) Collections.synchronizedList(p);
		quest = null;
		capacity = Team.MAX_CAPACITY;
	}

	public synchronized Player getLeader(){
		return players.get(0);
	}

	public synchronized void setLeader(Player p) throws TeamExceptionEvent{
		if (!contains(p))
			throw new TeamExceptionEvent(Cause.NOTONTEAM);
		players.remove(p);
		players.add(0, p);
	}

	public synchronized List<Player> getPlayers(){
		return players;
	}
	
	/*
	 * Mark team in a way such that nobody can get on the team anymore.
	 * This should help Java trigger GC on this object.
	 */
	protected void lockTeam(){
		capacity = 0;
	}

	public synchronized void setCapacity(int c) throws TeamExceptionEvent{
		if (c<=0 || c>players.size())
			throw new TeamExceptionEvent(Cause.BADCAPACITY);
		capacity = c;
	}

	public synchronized int getCapacity(){
		return capacity;
	}

	public long getTeamID(){
		return teamid;
	}

	public synchronized boolean contains(Player p){
		return players.contains(p);
	}

	public synchronized void startQuest(Quest quest) throws TeamExceptionEvent {
		if (quest!=null)
			throw new TeamExceptionEvent(Cause.ALREADYONQUEST);
		this.quest = quest;
		QuestStartedEvent event = new QuestStartedEvent(quest);
		Bukkit.getPluginManager().callEvent(event);
	}

	public synchronized void abandonQuest() throws TeamExceptionEvent {
		if (quest==null)
			throw new TeamExceptionEvent(Cause.NOQUEST);
		quest.finishQuest(CompleteStatus.CANCELED);
		quest = null;
	}

	public synchronized void teleportPlayers(Location l) {
		for (Player p : players){
			p.teleport(l);
		}
	}

	public synchronized void add(Player p) throws TeamExceptionEvent {
		if (players.size()>=capacity)
			throw new TeamExceptionEvent(Cause.OVERCAPACITY);
		if (contains(p))
			throw new TeamExceptionEvent(Cause.ALREADYINTEAM);
		//MineQuest.playerManager.getPlayerDetails(p).setTeam(teamid);
		players.add(p);
		// TODO add TeamPlayerJoinedEvent
	}

	public synchronized void remove(Player p) throws TeamExceptionEvent{
		if (!contains(p))
			throw new TeamExceptionEvent(Cause.NOTONTEAM);
		//MineQuest.playerManager.getPlayerDetails(p).setTeam(-1);
		players.remove(p);
		
		if (players.size()<=0)
			MineQuest.teamManager.removeEmptyTeam(teamid);
	}

}
