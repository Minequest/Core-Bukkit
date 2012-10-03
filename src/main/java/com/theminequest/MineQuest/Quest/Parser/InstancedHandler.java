package com.theminequest.MineQuest.Quest.Parser;

import static com.theminequest.MineQuest.API.Quest.QuestDetails.QUEST_LOADWORLD;
import static com.theminequest.MineQuest.API.Quest.QuestDetails.QUEST_NETHERWORLD;
import static com.theminequest.MineQuest.API.Quest.QuestDetails.QUEST_WORLD;

import java.util.List;

import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;

public class InstancedHandler implements QHandler {
	
	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		q.setProperty(QUEST_LOADWORLD, true);
		q.setProperty(QUEST_WORLD, line.get(0));
		if (line.size()>1)
			q.setProperty(QUEST_NETHERWORLD, Boolean.parseBoolean(line.get(1)));
	}
	
}
