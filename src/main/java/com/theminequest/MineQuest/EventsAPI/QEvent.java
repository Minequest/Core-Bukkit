package com.theminequest.MineQuest.EventsAPI;

import org.bukkit.Bukkit;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.EventCompleteEvent;

public abstract class QEvent{

	private long questid;
	private int eventid;
	private boolean complete;
	private int tasknumber;
	
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
	
	/**
	 * Tasks call fireEvent(). Then they wait for all events to
	 * complete, then fire off more stuff.
	 */
	public void fireEvent(){
		tasknumber = Bukkit.getScheduler().scheduleAsyncRepeatingTask(MineQuest.activePlugin, new Runnable(){
			@Override
			public void run() {
				if (conditions()){
					action();
					complete();
				}
			}
		}, 20, 100);
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
		Bukkit.getScheduler().cancelTask(tasknumber);
		EventCompleteEvent e = new EventCompleteEvent(this);
		Bukkit.getPluginManager().callEvent(e);
	}
	
}
