package com.theminequest.MineQuest.BukkitEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerManaEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	private Player player;
	private int mana;
	private boolean canceled;
	
	public PlayerManaEvent(Player p, int manachange){
		player = p;
		mana = manachange;
		canceled = false;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public int getExperienceChange(){
		return mana;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return canceled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		canceled = arg0;
	}

}
