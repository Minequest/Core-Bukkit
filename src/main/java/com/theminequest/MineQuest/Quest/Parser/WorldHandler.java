package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;
import static com.theminequest.MineQuest.API.Quest.QuestDetails.*;
import com.theminequest.MineQuest.API.Quest.QuestDetails;

public class WorldHandler implements QHandler {

	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		q.setProperty(QUEST_WORLD, line.get(0));
		if (line.size()>1)
			q.setProperty(QUEST_NETHERWORLD, true);
	}

}
