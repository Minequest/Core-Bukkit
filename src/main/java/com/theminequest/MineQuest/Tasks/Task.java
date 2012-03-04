package com.theminequest.MineQuest.Tasks;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.theminequest.MineQuest.BukkitEvents.EventCompleteEvent;
import com.theminequest.MineQuest.EventsAPI.QEvent;

public abstract class Task implements Listener {
	
	private boolean complete;
	private long questid;
	private int taskid;
	private HashMap<Integer,Boolean> events;
	
	/**
	 * Task for a Quest.
	 * @param questid Associated Quest
	 * @param taskid Task ID
	 * @param events Event numbers that must be completed
	 */
	public Task(long questid, int taskid, int[] events){
		complete = false;
		this.questid = questid;
		this.taskid = taskid;
		this.events = new HashMap<Integer,Boolean>();
		for (int e : events){
			this.events.put(e, false);
		}
	}
	
	public void load(){
		Bukkit.getPluginManager().registerEvents(this,
				Bukkit.getPluginManager().getPlugin("MineQuest"));
		start();
	}
	
	public abstract void start();
	
	public boolean isComplete(){
		return complete;
	}
	
	public void setComplete(boolean c){
		complete = c;
	}
	
	public long getQuestID(){
		return questid;
	}
	
	public int getTaskID(){
		return id;
	}
	
	public abstract void onEventCompletion(QEvent e);
	
	@EventHandler
	public void onEventComplete(EventCompleteEvent e){
		if (e.getEvent().getQuestId()==questid){
			if (e.getEvent().getTaskId()==id){
				onEventCompletion(e.getEvent());
			}
		}
	}

}
