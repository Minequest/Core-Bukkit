package com.theminequest.MineQuest.EventsAPI;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;

import com.theminequest.MineQuest.MineQuest;

/**
 * Because we don't know what classes will be available on runtime, we need to
 * keep track of all classes that extend QEvent and record them here.
 * 
 * @author xu_robert <xu_robert@linux.com>
 * 
 */
public class EventManager {

	private LinkedHashMap<String, Class<? extends QEvent>> classes;

	public EventManager() {
		MineQuest.log("[Event] Starting Manager...");
		classes = new LinkedHashMap<String, Class<? extends QEvent>>();
	}

	/**
	 * Register an event with MineQuest. It needs to have a name, such as
	 * QuestFinishEvent, that the quest file can use. <br>
	 * <b>WARNING: QEvents and classes based off of it must NOT tamper the
	 * constructor. Instead, use {@link QEvent#parseDetails(String)} to set
	 * instance variables and conditions.</b>
	 * 
	 * @param eventname
	 *            Event name
	 * @param event
	 *            Class of the event (.class)
	 */
	public void registerEvent(String eventname, Class<? extends QEvent> event) {
		if (classes.containsKey(eventname) || classes.containsValue(event))
			throw new IllegalArgumentException("We already have this class!");
		try {
			event.getConstructor(long.class, int.class, java.lang.String.class);
		} catch (Exception e) {
			throw new IllegalArgumentException("Constructor tampered with!");
		}
		classes.put(eventname, event);
	}

	/**
	 * Retrieve a new instance of an event for use with a quest and task.
	 * 
	 * @param eventname
	 *            Event to use
	 * @param q
	 *            Quest ID to attribute
	 * @param eventnum
	 *            Event Number for Quest
	 * @param d
	 *            Details for use with {@link QEvent#parseDetails(String)}
	 * @return new instance of the event requested
	 */
	public QEvent getNewEvent(String eventname, long q, int eventnum, String d) {
		if (!classes.containsKey(eventname))
			return null;
		Class<? extends QEvent> cl = classes.get(eventname);
		Constructor<? extends QEvent> ctor = null;
		try {
			ctor = cl.getConstructor(long.class, int.class,
					java.lang.String.class);
		} catch (NoSuchMethodException e) {
			// we have no idea how to handle this method.
			return null;
		}
		try {
			return (QEvent) ctor.newInstance(q, eventnum, d);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Check if the event implements an interface.
	 * 
	 * @param eventname
	 *            Event to check
	 * @param interfaze
	 *            Interface to check
	 * @return true if this event does implement the specified interface
	 */
	public boolean hasInterface(String eventname, String interfaze) {
		if (!classes.containsKey(eventname))
			return false;
		Class<? extends QEvent> cl = classes.get(eventname);
		Class<?>[] interfazes = cl.getInterfaces();
		for (Class<?> c : interfazes) {
			if (c.getSimpleName().equals(interfaze))
				return true;
		}
		return false;
	}

}
