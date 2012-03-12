package com.theminequest.MineQuest.EventsAPI;

import org.bukkit.Bukkit;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.BukkitEvents.EventCompleteEvent;

public abstract class QEvent{

	private long questid;
	private int eventid;
	private CompleteStatus complete;
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
		complete = null;
		parseDetails(details.split(":"));
	}
	
	/**
	 * Tasks call fireEvent(). Then they wait for all events to
	 * complete, then fire off more stuff.
	 */
	public final void fireEvent(){
		tasknumber = Bukkit.getScheduler().scheduleAsyncRepeatingTask(MineQuest.activePlugin, new Runnable(){
			@Override
			public void run() {
				if (conditions()){
					complete(action());
				}
			}
		}, 20, 100);
	}
	
	/**
	 * Returns the status of this event.
	 * @return Respective status, or <code>null</code> if it has
	 * not been declared yet.
	 */
	public final CompleteStatus isComplete(){
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
	 * Perform the event (and complete it, returning true if successful,
	 * false if not, and null to ignore it completely. Remember that failing
	 * an event fails the whole task, and possibly the whole mission.)
	 * @return the event action result
	 */
	public abstract CompleteStatus action();
	
	public final long getQuestId(){
		return questid;
	}
	
	public final int getEventId(){
		return eventid;
	}
	
	public final int getTaskId(){
		return tasknumber;
	}
	
	/**
	 * Notify that the event has been completed with the status given.
	 * @param actionresult Status to pass in.
	 */
	public void complete(CompleteStatus c){
		Bukkit.getScheduler().cancelTask(tasknumber);
		complete = c;
		EventCompleteEvent e = new EventCompleteEvent(this,c);
		Bukkit.getPluginManager().callEvent(e);
	}
	
}
