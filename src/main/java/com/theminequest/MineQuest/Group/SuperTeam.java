package com.theminequest.MineQuest.Group;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.Quest.Quest;

public class SuperTeam implements Group {

	@Override
	public Player getLeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLeader(Player p) throws GroupException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Player> getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Player p) throws GroupException {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Player p) throws GroupException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void invite(Player p) throws GroupException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCapacity(int capacity) throws GroupException {
		// TODO Auto-generated method stub

	}

	@Override
	public long getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void startQuest(Quest quest) throws GroupException {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterQuest() throws GroupException {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitQuest() throws GroupException {
		// TODO Auto-generated method stub

	}

	@Override
	public void abandonQuest() throws GroupException {
		// TODO Auto-generated method stub

	}

	@Override
	public Quest getQuest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInQuest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void teleportPlayers(Location l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void recordCurrentLocations() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveBackToLocations() throws GroupException {
		// TODO Auto-generated method stub

	}

	@Override
	public void lockTeam() {
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo(Group o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
