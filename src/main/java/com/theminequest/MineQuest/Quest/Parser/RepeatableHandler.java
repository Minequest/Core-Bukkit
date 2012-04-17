package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Quest.QuestDescription;
import com.theminequest.MineQuest.Quest.QuestParser.QHandler;

public class RepeatableHandler implements QHandler {

	@Override
	public void parseDetails(QuestDescription q, List<String> line) {
		q.questRepeatable = (line.get(0).equalsIgnoreCase("true"));
	}

}
