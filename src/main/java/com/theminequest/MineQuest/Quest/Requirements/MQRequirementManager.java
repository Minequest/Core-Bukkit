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
package com.theminequest.MineQuest.Quest.Requirements;

import java.util.LinkedHashMap;
import java.util.logging.Level;

import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Requirements.QuestRequirement;
import com.theminequest.MineQuest.API.Requirements.RequirementManager;

public class MQRequirementManager implements RequirementManager {
	
	private LinkedHashMap<String,Class<? extends QuestRequirement>> classes;
	
	public MQRequirementManager() {
		Managers.log("[Requirements] Starting Manager...");
		classes = new LinkedHashMap<String,Class<? extends QuestRequirement>>();
	}
	
	@Override
	public void register(String reqname, Class<? extends QuestRequirement> requirement) {
		if (classes.containsKey(reqname) || classes.containsValue(requirement))
			throw new IllegalArgumentException("We already have this class!");
		try {
			requirement.getConstructor();
		} catch (Exception e) {
			throw new IllegalArgumentException("Constructor tampered with!");
		}
		classes.put(reqname, requirement);
	}
	
	@Override
	public QuestRequirement construct(String requirementName, int ID, QuestDetails details, String properties) {
		if (!classes.containsKey(requirementName))
			return null;
		Class<? extends QuestRequirement> cl = classes.get(requirementName);
		try {
			QuestRequirement e = cl.getConstructor().newInstance();
			e.setupProperties(ID,details,properties);
			return e;
		} catch (Exception e) {
			Managers.log(Level.SEVERE, "[Requirements] In creating " + requirementName + " for Quest " + details.getProperty(QuestDetails.QUEST_NAME) + ":");
			e.printStackTrace();
			return null;
		}
	}
	
}
