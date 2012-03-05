package com.theminequest.MineQuest.Ability;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

public interface Ability_Interface extends Listener {
	
	String getName();
	void toggle(Player p, Entity target);
	@EventHandler
	void onEvent(Event e);
	
}
