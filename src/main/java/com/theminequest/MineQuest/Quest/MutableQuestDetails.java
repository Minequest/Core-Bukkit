package com.theminequest.MineQuest.Quest;

import java.io.Serializable;

import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Tracker.QuestStatisticUtils;

/**
 * Represents a Mutable QuestDetail that needs to be serialized
 * upon modification to a datastore, such as a database.
 * 
 * Modifying this detail will call createSnapshot() on every call.
 *
 */
public class MutableQuestDetails implements QuestDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3532065120496464254L;
	
	private QuestDetails details;
	private transient Quest quest;
	
	/**
	 * Initialize MutableQuestDetails as a wrapper to a Quest.
	 * @param details
	 */
	public MutableQuestDetails(Quest quest) {
		this.details = quest.getDetails();
		this.quest = quest;
	}

	@Override
	public boolean equals(Object arg0) {
		return quest.equals(arg0);
	}
	
	@Override
	public int compareTo(QuestDetails arg0) {
		return details.compareTo(arg0);
	}
	
	@Override
	public <E> E getProperty(String key) {
		return details.getProperty(key);
	}
	
	@Override
	public <E> E setProperty(String key, Serializable property) {
		E formerProperty = details.setProperty(key, property);
		if (quest != null)
			QuestStatisticUtils.checkpointQuest(quest);
		return formerProperty;
	}
	
	@Override
	public boolean containsProperty(String key) {
		return details.containsProperty(key);
	}
	
	@Override
	public <E> E removeProperty(String key) {
		E removedProperty = details.removeProperty(key);
		if (quest != null)
			QuestStatisticUtils.checkpointQuest(quest);
		return removedProperty;
	}
	
}
