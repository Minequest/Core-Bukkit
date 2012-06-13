package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;
import static com.theminequest.MineQuest.API.Quest.QuestDetails.*;

import com.theminequest.MineQuest.API.Quest.QuestDetails;

public class LoadWorldHandler implements QHandler {

	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		// I say YES to instances.
		q.setProperty(QUEST_LOADWORLD, true);
		q.setProperty(QUEST_WORLD, line.get(1));
		if (line.size()>2)
			q.setProperty(QUEST_NETHERWORLD, true);
	}

}
