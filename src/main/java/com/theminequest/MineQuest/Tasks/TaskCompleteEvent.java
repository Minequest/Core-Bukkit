package com.theminequest.MineQuest.Tasks;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TaskCompleteEvent extends Event {

	private long questid;
	private int id;
	private int result;
	
	public TaskCompleteEvent(long questid, int id, int result) {
		this.questid = questid;
		this.id = id;
		this.result = result;
	}
	
	public long getQuestID(){
		return questid;
	}
	
	public int getID(){
		return id;
	}
	
	public int getResult(){
		return result;
	}

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return null;
	}

}
