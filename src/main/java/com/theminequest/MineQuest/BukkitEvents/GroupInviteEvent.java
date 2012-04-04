package com.theminequest.MineQuest.BukkitEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GroupInviteEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	private String invitername;
	private Player invited;
	private long teamid;
	
	public GroupInviteEvent(String inviter, Player invited, long teamid){
		invitername = inviter;
		this.invited = invited;
		this.teamid = teamid;
	}
	
	public String getInviterName(){
		return invitername;
	}
	
	public Player getInvited(){
		return invited;
	}
	
	public long getTeamId(){
		return teamid;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
