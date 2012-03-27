package com.theminequest.MineQuest.Backend;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Group.Team;
import com.theminequest.MineQuest.Player.PlayerDetails;

public final class TeamBackend {
	
	public synchronized static void createTeam(Player p) throws BackendFailedException{
		if (teamID(p)!=-1)
			throw new BackendFailedException("Already on a team!");
		MineQuest.groupManager.createTeam(p);
	}

	public synchronized static Team getCurrentTeam(Player p){
		return MineQuest.groupManager.getTeam(teamID(p));
	}
	
	public synchronized static long teamID(Player p){
		PlayerDetails d = MineQuest.playerManager.getPlayerDetails(p);
		long id = d.getTeam();
		return id;
	}
	
	public synchronized static void invitePlayer(Player inviter, Player invitee) throws BackendFailedException {
		long id = teamID(inviter);
		if (id==-1)
			throw new BackendFailedException("You're not on a team!");
		if (teamID(invitee)!=-1)
			throw new BackendFailedException("The player you are trying to invite is already on a team!");
		MineQuest.playerManager.getPlayerDetails(invitee).invitePlayer(inviter.getName(), id, false);
	}

	// TODO: HANDLE if team size >= MAX
	public synchronized static void joinTeam(Player p, long teamid) throws BackendFailedException {
		Team t = MineQuest.groupManager.getTeam(teamid);
		if (t==null)
			throw new BackendFailedException("[TeamBackend] No such team!");
		if (MineQuest.playerManager.getPlayerDetails(p).getTeam()!=-1)
			throw new BackendFailedException("[TeamBackend] Already on a team!");
		t.add(p);
		MineQuest.playerManager.getPlayerDetails(p).invitePlayer("", teamid, true);
	}
	
	public synchronized static void removePlayerFromTeam(Player p){
		MineQuest.groupManager.removePlayerFromTeam(p);
	}

}
