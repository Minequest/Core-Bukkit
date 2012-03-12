package com.theminequest.MineQuest.EventsAPI;

import java.util.List;

import org.bukkit.entity.LivingEntity;

public interface TargetEvent {

	List<LivingEntity> getTargets();
	
}
