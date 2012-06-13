package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;
import com.theminequest.MineQuest.API.Quest.QuestRequirement;

import static com.theminequest.MineQuest.API.Quest.QuestDetails.*;
import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.Quest.RequirementFactory;

@Deprecated
public class RepeatableHandler implements QHandler {

	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		if (line.get(0).equalsIgnoreCase("false")){
			List<QuestRequirement> r = q.getProperty(QUEST_REQUIREMENTS);
			r.add(RequirementFactory.constructRequirement("neverdone", q, null));
		}
	}

}
