package com.theminequest.MineQuest.EventsAPI;

public abstract class DelayedQEvent extends QEvent {
	
	private long starttime;

	public DelayedQEvent(long q, int e, String details) {
		super(q, e, details);
		starttime = System.currentTimeMillis();
	}
	
	/**
	 * Get the delay in milliseconds.
	 * @return delay time in MS, parsed from details.
	 */
	public abstract long getDelay();
	
	/**
	 * Specify additional conditions
	 * @return true if additional conditions are met.
	 */
	public abstract boolean delayedConditions();
	
	@Override
	public final boolean conditions() {
		if (starttime+getDelay()>System.currentTimeMillis())
			return false;
		return delayedConditions();
	}

}
