package com.theminequest.MineQuest.Tasks;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Bukkit;
import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.BukkitEvents.TaskCompleteEvent;
import com.theminequest.MineQuest.EventsAPI.QEvent;
import com.theminequest.MineQuest.Quest.QuestManager;

public abstract class Task {
	
	private boolean complete;
	private long questid;
	private int taskid;
	private LinkedHashMap<Integer,Boolean> events;
	private List<QEvent> objects;
	
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
		this.events = new LinkedHashMap<Integer,Boolean>();
		for (int e : events){
			this.events.put(e, false);
		}
		this.objects = new ArrayList<QEvent>();
	}
	
	public void start(){
		for (Integer eventid : events.keySet()){
			String eventdesc = MineQuest.questManager.getQuest(questid).getEventDesc(eventid);
			String[] details = eventdesc.split(":");
			String eventname = details[0];
			String passind = "";
			for (int i=1; i<details.length; i++)
				passind+=details[i];
			QEvent e = MineQuest.eventManager.getNewEvent(eventname, questid, eventid, passind);
			if (e==null)
				// invalid event.
				events.remove(eventid);
			e.fireEvent();
			objects.add(e);
		}
	}
	
	public void finishEvent(int eventid, CompleteStatus completeStatus ){
		if (!complete && events.containsKey(eventid) && !events.get(eventid)){
			events.put(eventid, true);
			if (completeStatus==CompleteStatus.FAILURE){
				for (QEvent event : objects)
					event.complete(CompleteStatus.CANCELED);
				complete = true;
				TaskCompleteEvent e = new TaskCompleteEvent(questid,taskid,CompleteStatus.FAILURE);
				Bukkit.getPluginManager().callEvent(e);
			}else
				checkCompletion();
		}
	}
	
	private void checkCompletion() {
		for (Integer eventid : events.keySet()){
			if (!events.get(eventid))
				return;
		}
		complete = true;
		TaskCompleteEvent e = new TaskCompleteEvent(questid, taskid, CompleteStatus.SUCCESS);
		Bukkit.getPluginManager().callEvent(e);
	}

	public boolean isComplete(){
		return complete;
	}
	
	public long getQuestID(){
		return questid;
	}
	
	public int getTaskID(){
		return taskid;
	}
	
	public List<QEvent> getEventsRunning(){
		return objects;
	}

}
