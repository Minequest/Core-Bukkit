package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;

public class AcceptTextHandler implements QHandler {

	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		q.setProperty(QuestDetails.QUEST_ACCEPT, line.get(0));
	}

}
