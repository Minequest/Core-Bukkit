package com.theminequest.MineQuest.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public abstract class Task implements Listener {
	
	private boolean complete;
	private long questid;
	private int id;
	
	public Task(long questid, int id){
		complete = false;
		this.questid = questid;
		this.id = id;
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

}
