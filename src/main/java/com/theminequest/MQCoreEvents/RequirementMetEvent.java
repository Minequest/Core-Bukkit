package com.theminequest.MQCoreEvents;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Events.QuestEvent;
import com.theminequest.MineQuest.API.Quest.QuestRequirement;
import com.theminequest.MineQuest.Quest.RequirementFactory;

public class RequirementMetEvent extends QuestEvent {
	
	private int taskifmet;
	private int taskifnotmet;
	private int tasktosend;
	private QuestRequirement requirement;

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.API.Events.QuestEvent#parseDetails(java.lang.String[])
	 * [0] task id if met
	 * [1] task id if not met
	 * [n] requirement details...
	 */
	@Override
	public void parseDetails(String[] details) {
		taskifmet = Integer.parseInt(details[0]);
		taskifnotmet = Integer.parseInt(details[1]);
		tasktosend = -1;
		String type = details[2];
		String req = "";
		for (int i=3; i<details.length; i++){
			req+=details[i];
			if (i<details.length-1)
				req+=":";
		}
		QuestRequirement qr = RequirementFactory.constructRequirement(type, getQuest().getDetails(), req);
		if (qr!=null)
			requirement = qr;
		else
			throw new RuntimeException("Invalid Requirement!");
	}

	@Override
	public boolean conditions() {
		return true;
	}

	@Override
	public CompleteStatus action() {
		Player leader = Managers.getQuestGroupManager().get(getQuest()).getLeader();
		if (requirement.isSatisfied(leader))
			tasktosend = taskifmet;
		else
			tasktosend = taskifnotmet;
		return CompleteStatus.SUCCESS;
	}

	@Override
	public Integer switchTask() {
		return tasktosend;
	}

}
