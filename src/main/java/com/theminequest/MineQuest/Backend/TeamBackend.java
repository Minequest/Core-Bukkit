package com.theminequest.MineQuest.Backend;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Player.PlayerDetails;
import com.theminequest.MineQuest.Team.Team;

public final class TeamBackend {

	public static Team getCurrentTeam(Player p){
		PlayerDetails d = MineQuest.playerManager.getPlayerDetails(p);
		long teamid = d.getTeam();
		if (teamid==-1)
			return null;
		return MineQuest.teamManager.getTeam(teamid);
	}

	public static void joinTeam(Player p, long teamid) throws IllegalArgumentException {
		Team t = MineQuest.teamManager.getTeam(teamid);
		if (t==null)
			throw new IllegalArgumentException("[TeamBackend] No such team!");
		if (MineQuest.playerManager.getPlayerDetails(p).getTeam()!=-1)
			throw new IllegalArgumentException("[TeamBackend] Already on a team!");
		t.add(p);
	}

}
