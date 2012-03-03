package com.theminequest.MineQuest.EventsAPI;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.theminequest.MineQuest.BukkitEvents.EventCompleteEvent;

public abstract class QEvent extends Event{

	private long questid;
	private int eventid;
	private boolean complete;
	
	/**
	 * Initialize this QEvent with the associated Quest
	 * @param q Associated Quest
	 * @param e Event number
	 * @param details Details to parse
	 */
	public QEvent(long q, int e, String details){
		questid = q;
		eventid = e;
		complete = false;
		parseDetails(details.split(":"));
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
	
	/**
	 * Parse the details given (: separated)
	 * @param details Parameters given
	 */
	public abstract void parseDetails(String[] details);
	
	/**
	 * Conditions for this event to be performed (and therefore complete)
	 * @return true if all conditions are met for this event to complete
	 */
	public abstract boolean conditions();
	
	/**
	 * Perform the event (and complete it)
	 */
	public abstract void action();
	
	public long getQuestId(){
		return questid;
	}
	
	public int getEventId(){
		return eventid;
	}
	
	public void complete(){
		complete = true;
		EventCompleteEvent e = new EventCompleteEvent(this);
		Bukkit.getPluginManager().callEvent(e);
	}
	
}
