package com.theminequest.MineQuest.BukkitEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.theminequest.MineQuest.Group.Group;

public class GroupPlayerQuitEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	private Group group;
	private Player player;
	
	public GroupPlayerQuitEvent(Group g, Player p) {
		group = g;
		player = p;
	}
	
	public Group getGroup(){
		return group;
	}
	
	public Player getPlayer(){
		return player;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
