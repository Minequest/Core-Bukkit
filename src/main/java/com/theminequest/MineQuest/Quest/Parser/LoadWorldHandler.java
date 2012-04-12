package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Quest.QuestParser.QHandler;

public class LoadWorldHandler implements QHandler {

	@Override
	public void parseDetails(Quest q, List<String> line) {
		// I say YES to instances.
		q.loadworld = true;
		q.world = line.get(1);
		if (line.size()>2)
			q.nether = true;
	}

}
