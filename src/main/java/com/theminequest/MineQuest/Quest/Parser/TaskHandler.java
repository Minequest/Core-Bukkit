package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Quest.QuestParser.QHandler;

public class TaskHandler implements QHandler {

	@Override
	public void parseDetails(Quest q, List<String> line) {
		int id = Integer.parseInt(line.get(0));
		String[] e = line.get(1).split(",");
		q.tasks.put(id, e);
	}

}
