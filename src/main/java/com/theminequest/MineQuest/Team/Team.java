package com.theminequest.MineQuest.Team;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.Player.PlayerManager;

public class Team {

	private static final int teamcapacity = 8;
	private long teamid;
	private ArrayList<Player> players;
	
	public Team(long id, ArrayList<Player> p){
		if (p.size()<=0)
			throw new IllegalArgumentException("Empty Team!");
		teamid = id;
		players = p;
	}
	
	/*
	 * Need to add listeners when someone quits to leave the party as well.
	 */
	
	public Player getLeader(){
		return players.get(0);
	}
	
	public void setLeader(Player p){
		if (players.contains(p))
			throw new IllegalArgumentException("Not in team!");
		players.remove(p);
		players.add(0, p);
	}
	
	public Player[] getPlayers(){
		return players.toArray(new Player[players.size()]);
	}
	
	public boolean add(Player p){
		if (players.size()>=teamcapacity)
			return false;
		if (players.contains(p))
			return false;
		if (PlayerManager.getPlayerDetails(p).getTeam()!=-1)
			return false;
		PlayerManager.getPlayerDetails(p).setTeam(teamid);
		players.add(p);
		return true;
	}
	
	public boolean remove(Player p){
		if (!players.contains(p))
			return false;
		if (PlayerManager.getPlayerDetails(p).getTeam()==-1)
			return false;
		PlayerManager.getPlayerDetails(p).setTeam(-1);
		players.remove(p);
		if (players.size()<=0)
			TeamManager.removeTeam(teamid);
		return true;
	}
	
}
