package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;
import com.theminequest.MineQuest.Tasks.V1Task;

public class V1Handler implements QHandler {
	
	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		q.setProperty(V1Task.DETAILS_TOGGLE, Boolean.parseBoolean(line.get(0)));
	}
	
}
