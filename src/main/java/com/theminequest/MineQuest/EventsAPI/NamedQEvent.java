package com.theminequest.MineQuest.EventsAPI;

public interface NamedQEvent {
	
	/**
	 * Describe the event to the player; e.g.,
	 * where the player has to go (AreaEvent)
	 * @return one-line String description.
	 */
	String getDescription();

}
