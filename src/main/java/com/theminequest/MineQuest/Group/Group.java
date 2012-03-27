package com.theminequest.MineQuest.Group;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.Quest.Quest;

public interface Group {
	
	/*
	 * Basic Group Commands
	 */
	Player getLeader();
	void setLeader(Player p) throws GroupExceptionEvent;
	List<Player> getPlayers();
	void add(Player p) throws GroupExceptionEvent;
	void remove(Player p) throws GroupExceptionEvent;
	boolean contains(Player p);

	/*
	 * Group Configuration Commands
	 */
	int getCapacity();
	void setCapacity(int capacity) throws GroupExceptionEvent;
	/*
	 * Group identification
	 */
	long getID();
	
	/*
	 * Group questing
	 */
	void startQuest(Quest quest) throws GroupExceptionEvent;
	void abandonQuest() throws GroupExceptionEvent;
	Quest getQuest();
	void teleportPlayers(Location l);
	
	/*
	 * GroupManager usage
	 */
	void lockTeam();

}
