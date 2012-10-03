package com.theminequest.MQCoreRequirements;

import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Requirements.RequirementManager;

public class RegisterRequirements {
	
	public static void registerRequirements() {
		RequirementManager r = Managers.getRequirementManager();
		r.register("GroupSizeRequirement", GroupSizeRequirement.class);
		r.register("HasItemRequirement", HasItemRequirement.class);
		r.register("ItemInHandRequirement", ItemInHandRequirement.class);
		r.register("LevelRequirement", LevelRequirement.class);
		r.register("MoneyRequirement", MoneyRequirement.class);
		r.register("NotRepeatableRequirement", NotRepeatableRequirement.class);
		r.register("PermissionRequirement", PermissionRequirement.class);
		r.register("PlayerRequirement", PlayerRequirement.class);
		r.register("QuestCompletedRequirement", QuestCompletedRequirement.class);
		r.register("QuestFailedRequirement", QuestFailedRequirement.class);
		r.register("RealDateRequirement", RealDateRequirement.class);
		r.register("TimeRequirement", TimeRequirement.class);
		r.register("WeatherRequirement", WeatherRequirement.class);
		r.register("WorldRequirement", WorldRequirement.class);
	}
	
}
