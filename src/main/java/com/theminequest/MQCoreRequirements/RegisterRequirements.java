/*
 * This file is part of MineQuest, The ultimate MMORPG plugin!.
 * MineQuest is licensed under GNU General Public License v3.
 * Copyright (C) 2012 The MineQuest Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
