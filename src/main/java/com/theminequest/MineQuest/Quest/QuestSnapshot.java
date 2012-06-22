package com.theminequest.MineQuest.Quest;

import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Quest.Quest;
import com.theminequest.MineQuest.API.Quest.QuestDetails;

public class QuestSnapshot implements com.theminequest.MineQuest.API.Quest.QuestSnapshot {

	private QuestDetails details;
	private int lasttask;
	private String owner;
	
	public QuestSnapshot(Quest quest){
		details = quest.getDetails();
		if (quest.getActiveTask()!=null)
			lasttask = quest.getActiveTask().getTaskID();
		else
			lasttask = -1;
		owner = quest.getQuestOwner();
	}
	
	@Override
	public int compareTo(com.theminequest.MineQuest.API.Quest.QuestSnapshot o) {
		return details.compareTo(o.getDetails());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof QuestSnapshot))
			return false;
		QuestSnapshot s = (QuestSnapshot) obj;
		return (details.equals(s.getDetails()) && owner.equals(s.getQuestOwner()));
	}

	@Override
	public QuestDetails getDetails() {
		return details;
	}

	@Override
	public int getLastTaskID() {
		return lasttask;
	}

	@Override
	public String getQuestOwner() {
		return owner;
	}

	@Override
	public Quest recreateQuest() {
		Quest q = com.theminequest.MineQuest.Quest.Quest.newInstance(-1,getDetails(),getQuestOwner());
		if (lasttask!=-1){
			if (!q.startTask(lasttask))
				throw new RuntimeException("Could not start lasttask...");
		}
		return q;
	}

}
