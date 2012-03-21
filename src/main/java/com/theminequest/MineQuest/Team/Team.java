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
import java.util.List;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Player.PlayerManager;

public class Team {

	private static final int defaultteamcapacity = 8;
	private long teamid;
	private ArrayList<Player> players;
	private int capacity;
	
	protected Team(long id, ArrayList<Player> p){
		if (p.size()<=0)
			throw new IllegalArgumentException("Empty Team!");
		teamid = id;
		players = p;
		capacity = defaultteamcapacity;
	}
	
	/*
	 * Need to add listeners when someone quits to leave the party as well.
	 */
	
	public synchronized Player getLeader(){
		return players.get(0);
	}
	
	public synchronized void setLeader(Player p){
		if (players.contains(p))
			throw new IllegalArgumentException("Not in team!");
		players.remove(p);
		players.add(0, p);
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public void setCapacity(int c){
		if (c<=0)
			throw new IllegalArgumentException("Invalid Capacity!");
		capacity = c;
	}
	
	public int getCapacity(){
		return capacity;
	}
	
	public boolean contains(Player p){
		return players.contains(p);
	}
	
	public synchronized boolean add(Player p){
		if (players.size()>=capacity)
			return false;
		if (players.contains(p))
			return false;
		if (MineQuest.playerManager.getPlayerDetails(p).getTeam()!=-1)
			return false;
		MineQuest.playerManager.getPlayerDetails(p).setTeam(teamid);
		players.add(p);
		return true;
	}
	
	public synchronized boolean remove(Player p){
		if (!players.contains(p))
			return false;
		if (MineQuest.playerManager.getPlayerDetails(p).getTeam()==-1)
			return false;
		MineQuest.playerManager.getPlayerDetails(p).setTeam(-1);
		players.remove(p);
		return true;
	}
	
}
