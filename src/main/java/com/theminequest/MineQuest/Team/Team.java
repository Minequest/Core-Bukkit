package com.theminequest.MineQuest.Team;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.Player.PlayerManager;

public class Team {

	private static final int defaultteamcapacity = 8;
	private long teamid;
	private ArrayList<Player> players;
	private int capacity;
	
	public Team(long id, ArrayList<Player> p){
		if (p.size()<=0)
			throw new IllegalArgumentException("Empty Team!");
		teamid = id;
		players = p;
		capacity = defaultteamcapacity;
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
	
	public void setCapacity(int c){
		if (c<=0)
			throw new IllegalArgumentException("Invalid Capacity!");
		capacity = c;
	}
	
	public int getCapacity(){
		return capacity;
	}
	
	public boolean add(Player p){
		if (players.size()>=capacity)
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
