package com.theminequest.MineQuest.Team;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

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
	
	public long createTeam(ArrayList<Player> p){
		long id = teamid;
		teamid++;
		teams.put(id, new Team(teamid,p));
		for (Player player : p){
			MineQuest.playerManager.getPlayerDetails(player).setTeam(id);
		}
		return id;
	}
	
	public long createTeam(Player p){
		ArrayList<Player> group = new ArrayList<Player>();
		group.add(p);
		return createTeam(group);
	}
	
	public Team getTeam(long id){
		return teams.get(id);
	}
	
	public void removePlayerFromTeam(Player p){
		PlayerDetails d = MineQuest.playerManager.getPlayerDetails(p);
		long team = d.getTeam();
		if (team==-1)
			return;
		teams.get(team).remove(p);
		d.setTeam(-1);
	}
	
	public void removeTeam(long id){
		Team t = teams.get(id);
		List<Player> members = t.getPlayers();
		for (Player p : members)
			removePlayerFromTeam(p);
		teams.remove(id);
	}
	
}
