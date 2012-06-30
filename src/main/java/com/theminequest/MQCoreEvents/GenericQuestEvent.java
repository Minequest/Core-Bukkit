package com.theminequest.MQCoreEvents;

import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Events.DelayedQuestEvent;

public class GenericQuestEvent extends DelayedQuestEvent {
	
	private int delay;
	private int taskid;

	@Override
	public void parseDetails(String[] details) {
		this.delay = Integer.parseInt(details[0]);
		this.taskid = Integer.parseInt(details[1]);
	}

	@Override
	public boolean delayedConditions() {
		return true;
	}

	@Override
	public CompleteStatus action() {
		return CompleteStatus.SUCCESS;
	}

	@Override
	public Integer switchTask() {
		return taskid;
	}

	@Override
	public long getDelay() {
		return delay;
	}

}
