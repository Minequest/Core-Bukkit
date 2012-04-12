package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Quest.QuestParser.QHandler;

public class NameHandler implements QHandler {

	@Override
	public void parseDetails(Quest q, List<String> line) {
		q.displayname = line.get(0);
	}

}
