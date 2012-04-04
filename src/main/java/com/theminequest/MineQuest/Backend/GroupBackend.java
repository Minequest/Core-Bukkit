package com.theminequest.MineQuest.Backend;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.ManagerException;
import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Backend.BackendFailedException.BackendReason;
import com.theminequest.MineQuest.Group.Group;
import com.theminequest.MineQuest.Group.GroupException;
import com.theminequest.MineQuest.Group.Team;

public final class GroupBackend {
	
	public synchronized static void createTeam(Player p) throws BackendFailedException{
		if (teamID(p)!=-1)
			throw new BackendFailedException(BackendReason.INVALIDARGS);
		MineQuest.groupManager.createTeam(p);
	}

	public synchronized static Group getCurrentGroup(Player p){
		return MineQuest.groupManager.getGroup(teamID(p));
	}
	
	public synchronized static long teamID(Player p){
		return MineQuest.groupManager.indexOf(p);
	}
	
	public synchronized static void invitePlayer(Player inviter, Player invitee) throws BackendFailedException {
		long id = teamID(inviter);
		if (id==-1)
			throw new BackendFailedException(BackendReason.INVALIDARGS);
		if (teamID(invitee)!=-1)
			throw new BackendFailedException(BackendReason.INVALIDARGS);
		try {
			MineQuest.groupManager.getGroup(id).invite(invitee);
		} catch (GroupException e) {
			throw new BackendFailedException(BackendReason.MANAGEREXCEPTION,e);
		}
	}
	
	public synchronized static boolean hasInvite(Player p){
		return MineQuest.groupManager.hasInvite(p);
	}

/*	// TODO: HANDLE if team size >= MAX
	public synchronized static void joinTeam(Player p, long teamid) throws BackendFailedException {
		Team t = MineQuest.groupManager.getGroup(teamid);
		if (t==null)
			throw new BackendFailedException("[GroupBackend] No such team!");
		if (MineQuest.playerManager.getPlayerDetails(p).getTeam()!=-1)
			throw new BackendFailedException("[GroupBackend] Already on a team!");
		t.add(p);
		MineQuest.playerManager.getPlayerDetails(p).invite("", teamid, true);
	}*/
	
	public synchronized static void acceptInvite(Player p) throws BackendFailedException{
		try {
			MineQuest.groupManager.acceptPendingInvite(p);
		} catch (ManagerException e) {
			throw new BackendFailedException(BackendReason.NOINVITE);
		} catch (GroupException e) {
			throw new BackendFailedException(BackendReason.FULLORINQUEST);
		}
	}
	
	public synchronized static void removePlayerFromTeam(Player p) throws BackendFailedException{
		long teamid = teamID(p);
		if (teamid==-1)
			throw new BackendFailedException(BackendReason.NOTONTEAM);
		try {
			MineQuest.groupManager.getGroup(teamid).remove(p);
		} catch (GroupException e) {
			throw new BackendFailedException(BackendReason.MANAGEREXCEPTION,e);
		}
	}

}
