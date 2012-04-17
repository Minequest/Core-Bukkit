package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Quest.QuestDescription;
import com.theminequest.MineQuest.Quest.QuestParser.QHandler;

public class DescriptionHandler implements QHandler {

	@Override
	public void parseDetails(QuestDescription q, List<String> line) {
		q.displaydesc = line.get(0);
	}

}
