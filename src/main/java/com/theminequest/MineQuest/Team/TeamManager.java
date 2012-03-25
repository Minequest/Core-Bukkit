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
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Player.PlayerDetails;

public class TeamManager implements Listener{

	private LinkedHashMap<Long,Team> teams;
	private long teamid;
	
	public TeamManager(){
		MineQuest.log("[Team] Starting Manager...");
		teams = new LinkedHashMap<Long,Team>();
		teamid = 0;
	}
	
	public synchronized long createTeam(ArrayList<Player> p){
		long id = teamid;
		teamid++;
		teams.put(id, new Team(teamid,p));
		for (Player player : p){
			MineQuest.playerManager.getPlayerDetails(player).setTeam(id);
		}
		return id;
	}
	
	public synchronized long createTeam(Player p){
		ArrayList<Player> group = new ArrayList<Player>();
		group.add(p);
		return createTeam(group);
	}
	
	public Team getTeam(long id){
		return teams.get(id);
	}
	
	public synchronized void removePlayerFromTeam(Player p){
		PlayerDetails d = MineQuest.playerManager.getPlayerDetails(p);
		long team = d.getTeam();
		if (team==-1)
			return;
		teams.get(team).remove(p);
		d.setTeam(-1);
	}
	
	public synchronized void removeTeam(long id){
		Team t = teams.get(id);
		List<Player> members = t.getPlayers();
		for (Player p : members)
			removePlayerFromTeam(p);
		teams.remove(id);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent e){
		PlayerDetails p = MineQuest.playerManager.getPlayerDetails(e.getPlayer());
		if (p.getTeam()!=-1){
			MineQuest.teamManager.getTeam(p.getTeam()).remove(e.getPlayer());
		}
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerKick(PlayerKickEvent e){
		PlayerDetails p = MineQuest.playerManager.getPlayerDetails(e.getPlayer());
		if (p.getTeam()!=-1){
			MineQuest.teamManager.getTeam(p.getTeam()).remove(e.getPlayer());
		}
	}
	
}
