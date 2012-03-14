package com.theminequest.MineQuest.AbilityAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AbilityRefreshedEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	private Ability ability;
	private Player player;

	public AbilityRefreshedEvent(Ability a, Player p) {
		ability = a;
		player = p;
	}
	
	public Ability getAbility(){
		return ability;
	}
	
	public Player getPlayer(){
		return player;
	}

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}

}
