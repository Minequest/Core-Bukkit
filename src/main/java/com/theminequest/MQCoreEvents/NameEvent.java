package com.theminequest.MQCoreEvents;

import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Events.UserQuestEvent;
import com.theminequest.MineQuest.API.Events.QuestEvent;

/**
 * Make an entry appear in Current Tasks. This allows
 * for quest makers to manually specify a task in Current Tasks for
 * the player to accomplish. <b>This does NOT block
 * automatic completion of events due to a manual
 * specification inside Task.</b>
 * @author Robert Xu <xu_robert@linux.com>
 *
 */
public class NameEvent extends QuestEvent implements UserQuestEvent {
	
	private String task;

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * [0] task
	 */
	@Override
	public void parseDetails(String[] details) {
		task = details[0];
	}

	@Override
	public boolean conditions() {
		return false;
	}

	@Override
	public CompleteStatus action() {
		return CompleteStatus.IGNORE;
	}

	@Override
	public Integer switchTask() {
		return null;
	}

	@Override
	public String getDescription() {
		return task;
	}

}
