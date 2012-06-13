package com.theminequest.MineQuest.Quest;

import com.theminequest.MineQuest.API.Quest.QuestRequirement;
import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestRequirement.Type;

public class RequirementFactory {
	
	public static QuestRequirement constructRequirement(String type, QuestDetails quest, String details){
		Type t = Type.valueOf(type.toUpperCase());
		if (t!=null)
			return new QuestReq(t,quest,details);
		return null;
	}

}
