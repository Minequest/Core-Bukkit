package com.theminequest.MineQuest.EventsAPI;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public abstract class QEvent extends Event{

	public static void fireEvent(QEvent e){
		Bukkit.getPluginManager().callEvent(e);
	}
	
	public QEvent(){
		
	}
	
	public abstract boolean action();
	
	
}
