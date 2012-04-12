package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Quest.QuestParser.QHandler;

public class WorldHandler implements QHandler {

	@Override
	public void parseDetails(Quest q, List<String> line) {
		q.world = line.get(0);
		if (line.size()>1)
			q.nether = true;
	}

}
