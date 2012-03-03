package com.theminequest.MineQuest.EventsAPI;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.theminequest.MineQuest.BukkitEvents.EventCompleteEvent;
import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Tasks.Task;

public abstract class QEvent extends Event{

	private Quest quest;
	private Task task;
	private boolean complete;
	
	public QEvent(Quest q, Task t){
		quest = q;
		task = t;
		complete = false;
	}
	
	public void fireEvent(){
		if (conditions()){
			action();
			complete();
		}
	}
	
	public boolean isComplete(){
		return complete;
	}
	
	public abstract boolean conditions();
	
	public abstract boolean action();
	
	public long getQuestId(){
		return quest.getID();
	}
	
	public int getTaskId(){
		return task.getTaskID();
	}
	
	public void complete(){
		complete = true;
		EventCompleteEvent e = new EventCompleteEvent(this);
		Bukkit.getPluginManager().callEvent(e);
	}
	
}
