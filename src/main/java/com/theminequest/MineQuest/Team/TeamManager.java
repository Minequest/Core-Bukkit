package com.theminequest.MineQuest.Team;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

public class TeamManager {

	private static HashMap<Long,Team> teams = new HashMap<Long,Team>();
	private static long teamid = 0;
	
	public static void createTeam(ArrayList<Player> p){
		teams.put(teamid, new Team(teamid,p));
		teamid++;
	}
	
	public static void createTeam(Player p){
		ArrayList<Player> group = new ArrayList<Player>();
		group.add(p);
		createTeam(group);
	}
	
	public static void removeTeam(long id){
		teams.remove(id);
	}
	
}
