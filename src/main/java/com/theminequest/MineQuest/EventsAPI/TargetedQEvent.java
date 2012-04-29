package com.theminequest.MineQuest.EventsAPI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.LivingEntity;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Target.TargetManager;

public abstract class TargetedQEvent extends DelayedQEvent {

	public TargetedQEvent(long q, int e, String details) {
		super(q, e, details);
	}

	@Override
	public long getDelay() {
		return 0;
	}
	
	/**
	 * Some events are dual normal/targeted events.
	 * Figure out if getTargets() should check.
	 * @return true if event is targeted
	 */
	public abstract boolean enableTargets();
	
	/**
	 * Retrieve the target ID.
	 * @return target ID
	 */
	public abstract int getTargetId();
	
	/**
	 * Retrieve the targets with this event.
	 * @return list of LivingEntites (empty if disabled)
	 */
	public List<LivingEntity> getTargets(){
		if (!enableTargets())
			return new ArrayList<LivingEntity>();
		Quest q = MineQuest.questManager.getQuest(getQuestId());
		return TargetManager.getTarget(q, q.getTarget(getTargetId()));
	}

}
