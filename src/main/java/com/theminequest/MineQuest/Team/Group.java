package com.theminequest.MineQuest.Team;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.Quest.Quest;

public interface Group {
	
	/*
	 * Basic Group Commands
	 */
	Player getLeader();
	void setLeader(Player p);
	List<Player> getPlayers();
	void add(Player p);
	void remove(Player p);
	boolean contains(Player p);

	/*
	 * Group Configuration Commands
	 */
	int getCapacity();
	void setCapacity();
	/*
	 * Group identification
	 */
	long getID();
	
	/*
	 * Group questing
	 */
	void startQuest(Quest quest);
	void abandonQuest();
	Quest getQuest();
	void teleportPlayers(Location l);
	
	/*
	 * TeamManager usage
	 */
	void lockTeam();

}
