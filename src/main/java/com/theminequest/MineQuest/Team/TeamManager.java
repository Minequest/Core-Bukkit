/**
 * This file, TeamManager.java, is part of MineQuest:
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Player.PlayerDetails;
import com.theminequest.MineQuest.Quest.Quest;

public class TeamManager implements Listener{

	protected final int MAX_CAPACITY;
	private LinkedHashMap<Long,Team> teams;
	private long teamid;

	public TeamManager(){
		MineQuest.log("[Team] Starting Manager...");
		teams = (LinkedHashMap<Long, Team>) Collections.synchronizedMap(new LinkedHashMap<Long,Team>());
		teamid = 0;
		MAX_CAPACITY = MineQuest.configuration.groupConfig.getInt("team_max_capacity", 8);
	}

	public synchronized long createTeam(ArrayList<Player> p){
		long id = teamid;
		teamid++;
		teams.put(id, new Team(teamid,p));
		//for (Player player : p){
		//	MineQuest.playerManager.getPlayerDetails(player).setTeam(id);
		//}
		return id;
	}

	public synchronized long createTeam(Player p){
		ArrayList<Player> group = new ArrayList<Player>();
		group.add(p);
		return createTeam(group);
	}

	public synchronized Team getTeam(long id){
		return teams.get(id);
	}

	/**
	 * Determine if a player is on a team.
	 * @param p Player to check for.
	 * @return Team ID, or -1 if not on team.
	 */
	public synchronized long indexOf(Player p){
		for (long id : teams.keySet()){
			Team t = teams.get(id);
			if (t!=null && t.contains(p))
				return id;
		}
		return -1;
	}
	
	/**
	 * Determine if a quest is being played by a team
	 * @param q Quest
	 * @return Team ID, or -1 if not on a team.
	 */
	public synchronized long indexOfQuest(Quest q){
		for (long id : teams.keySet()){
			Team t = teams.get(id);
			if (t!=null && t.getQuest()!=null && t.getQuest().equals(q))
				return id;
		}
		return -1;
	}

	/*
	 * Only called by Team objects when everyone leaves the team.
	 */
	protected synchronized void removeEmptyTeam(long id){
		teams.get(id).lockTeam();
		teams.put(id, null);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public synchronized void onPlayerQuit(PlayerQuitEvent e){
		//PlayerDetails p = MineQuest.playerManager.getPlayerDetails(e.getPlayer());
		//if (p.getTeam()!=-1){
		//	MineQuest.teamManager.getTeam(p.getTeam()).remove(e.getPlayer());
		//}
		long team = indexOf(e.getPlayer());
		if (team!=-1){
			try {
				teams.get(team).remove(e.getPlayer());
			} catch (TeamExceptionEvent e1) {
				MineQuest.log(Level.SEVERE, "Failed to remove player from team: " + e1);
			}
		}

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public synchronized void onPlayerKick(PlayerKickEvent e){
		//PlayerDetails p = MineQuest.playerManager.getPlayerDetails(e.getPlayer());
		//if (p.getTeam()!=-1){
		//	MineQuest.teamManager.getTeam(p.getTeam()).remove(e.getPlayer());
		//}
		long team = indexOf(e.getPlayer());
		if (team!=-1){
			try {
				teams.get(team).remove(e.getPlayer());
			} catch (TeamExceptionEvent e1) {
				MineQuest.log(Level.SEVERE, "Failed to remove player from team: " + e1);
			}
		}
	}

}
