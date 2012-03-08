package com.theminequest.MineQuest.Tasks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.BukkitEvents.EventCompleteEvent;
import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Quest.QuestManager;

public class TaskManager implements Listener {
	
	@EventHandler
	public void onEventComplete(EventCompleteEvent e){
		long questid = e.getEvent().getQuestId();
		Quest q = QuestManager.getQuest(questid);
		Task t = q.getTask(e.getEvent().getTaskId());
		t.finishEvent(e.getEvent().getEventId(),(e.getCompleteStatus()==CompleteStatus.FAILURE));
	}

}
