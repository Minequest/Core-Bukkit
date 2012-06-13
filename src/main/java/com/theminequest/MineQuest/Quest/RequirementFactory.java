package com.theminequest.MineQuest.Quest;

import com.theminequest.MineQuest.API.Quest.QuestRequirement;
import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestRequirement.Type;

public class RequirementFactory {
	
	public static QuestRequirement constructRequirement(String type, QuestDetails quest, String details){
		if (type.equalsIgnoreCase("neverdone"))
			return new QuestReq(Type.NEVERDONE,quest,details);
		return null;
	}

}
