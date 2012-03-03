package com.theminequest.MineQuest.QuestEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.monksanctum.MineQuest.Quest.Quest;

public class QuestStartEvent extends QuestTypicalEvent {
	
	private static final HandlerList handlers = new HandlerList();
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}

	/**
	 * Creates a QuestStartEvent to denote that a Quest has been activated.
	 * @param p Players involved
	 * @param q Quest started
	 */
	public QuestStartEvent(Player[] p, Quest q) {
		super(p, q);
	}

}
