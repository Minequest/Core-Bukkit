package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;
import java.util.Map;

import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;
import static com.theminequest.MineQuest.API.Quest.QuestDetails.*;
import com.theminequest.MineQuest.API.Quest.QuestDetails;

public class TaskHandler implements QHandler {

	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		int id = Integer.parseInt(line.get(0));
		String[] e = line.get(1).split(",");
		Map<Integer,String[]> tasks = q.getProperty(QUEST_TASKS);
		tasks.put(id, e);
	}

}
