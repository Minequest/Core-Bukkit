package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;
import static com.theminequest.MineQuest.API.Quest.QuestDetails.*;

public class FinishTextHandler implements QHandler {

	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		q.setProperty(QUEST_COMPLETE,line.get(0));
	}

}
