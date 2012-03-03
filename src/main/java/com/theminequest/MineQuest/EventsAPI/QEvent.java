package com.theminequest.MineQuest.EventsAPI;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Tasks.Task;

public abstract class QEvent extends Event{

	private Quest quest;
	private Task task;
	
	public static void fireEvent(QEvent e){
		Bukkit.getPluginManager().callEvent(e);
	}
	
	public QEvent(){
		
	}
	
	public abstract boolean action();
	
	
}
