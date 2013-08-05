package com.theminequest.bukkit.impl.event;

public class NRCollectEvent extends CollectEvent {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * Details:
	 * [0]: task to incur upon completion
	 * [1]: itemids;
	 * [2]: totaltocollect
	 */
	@Override
	public void setupArguments(String[] details) {
		collectItems = false;
		super.setupArguments(details);
	}
}
