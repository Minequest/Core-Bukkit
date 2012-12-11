package com.theminequest.MineQuest.Tasks;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;

import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Events.QuestEvent;
import com.theminequest.MineQuest.API.Quest.Quest;
import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestUtils;
import com.theminequest.MineQuest.API.Task.QuestTask;

/**
 * V1Task operates according to the 0.70 MineQuest System,
 * in which tasks started would toggle events, and then exit
 * immediately. This means that starting another task would NOT
 * cause the other one to cancel along with all of its other events.
 * 
 * Event numbers toggled are stored in the QuestDetails object that
 * accompanies the Quest. Events run normally; and if an event needs
 * to be canceled, a CancelEvent (which only works with V1Task) can
 * be issued to cancel the event.
 *
 */
public class V1Task implements QuestTask {
	
	/**
	 * The location within the QuestDetails object where all currently
	 * running events are stored. Returns a {@link java.util.TreeSet} of Integers.
	 */
	public static final String DETAILS_NAME = "v1.tasks";
	
	/**
	 * Whether this Quest has this behaviour. Returns a {@link Boolean}.
	 */
	public static final String DETAILS_TOGGLE = "v1.toggle";
	
	private Quest quest;
	private int taskid;
	private Collection<Integer> events;
	private Collection<QuestEvent> questEvents;
	
	/**
	 * Initialize a new V1Task.
	 * @param quest Quest to link to
	 * @param taskid Task ID of this task (last called)
	 * @param events Collection of events to start, or null to initialize previously used ones.
	 * @param questEvents Objects of the Quest Events
	 */
	public V1Task(Quest quest, int taskid, Collection<Integer> events, V1Task lastTask) {
		this.quest = quest;
		this.taskid = taskid;
		this.events = events;
		if (lastTask != null)
			this.questEvents = lastTask.questEvents;
		else
			this.questEvents = null;
	}
	
	@Override
	public void start() {
		TreeSet<Integer> running = quest.getDetails().getProperty(DETAILS_NAME);
		if (running == null) {
			running = new TreeSet<Integer>();
			quest.getDetails().setProperty(DETAILS_NAME, running);
		}
		
		if (events == null) { // then start all events previously contained within v1.tasks
			events = running;
			running = new TreeSet<Integer>(); // dereference to avoid conflicts;
		}
		
		List<QuestEvent> objects = new LinkedList<QuestEvent>();
		
		for (Integer event : events) {
			
			if (running.contains(event)) // event already there, ignore recalling of it
				continue;
			
			String d = QuestUtils.getEvent(quest,event);
			if (d == null) {
				Managers.log(Level.WARNING, "[Task] Missing event number " + event + " in V1Task "+taskid+" for quest "+quest.getDetails().getProperty(QuestDetails.QUEST_NAME)+"; Ignoring.");
				running.remove(event);
				continue;
			}
			String[] eventdetails = d.split(":");
			String recombined = "";
			for (int r=1; r<eventdetails.length; r++){
				recombined+=eventdetails[r];
				if (r!=(eventdetails.length-1));
				recombined+=":";
			}
			
			QuestEvent eventObject = Managers.getEventManager().constructEvent(eventdetails[0], quest, event, recombined);
			
			if (eventObject!=null) {
				running.add(event);
				objects.add(eventObject);
			} else {
				Managers.log(Level.WARNING, "[Task] Missing event " + eventdetails[0] + "; Ignoring.");
			}
		}
		
		Managers.getEventManager().registerEventListeners(Collections.unmodifiableCollection(objects));
		for (QuestEvent o : objects)
			o.fireEvent();
		
		if (questEvents == null)
			questEvents = new LinkedList<QuestEvent>();
		questEvents.addAll(objects);
	}
	
	/**
	 * V1Task does nothing with cancelTask.
	 * Any call to cancel the task does nothing,
	 * as tasks are not linked to Events.
	 * (Difference between this and completeTask(CANCELED)?
	 * This one is called by QuestEvent.)
	 */
	@Override
	public void cancelTask() {}
	
	/**
	 * V1Task does relatively nothing with completeTask,
	 * unless CompleteStatus.CANCELED is passed in, at
	 * which it dismantles all possible events.
	 */
	@Override
	public void completeTask(CompleteStatus status) {
		if (status == CompleteStatus.CANCELED) {
			for (QuestEvent event : questEvents)
				event.complete(CompleteStatus.CANCELED);
		}
	}
	
	@Override
	public CompleteStatus isComplete() {
		return null;
	}
	
	@Override
	public Quest getQuest() {
		return quest;
	}
	
	@Override
	public int getTaskID() {
		return -3;
	}
	
	@Override
	public Collection<QuestEvent> getEvents() {
		return Collections.unmodifiableCollection(questEvents);
	}
	
	/**
	 * V1Task does nothing with checkTasks.
	 * In V1, it is up to the Quest Author to end the
	 * quest responsibly.
	 */
	@Override
	public void checkTasks() {}
	
}
