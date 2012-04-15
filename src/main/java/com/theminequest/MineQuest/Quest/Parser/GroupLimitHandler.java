package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Quest.QuestParser.QHandler;

public class GroupLimitHandler implements QHandler {

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.Quest.QuestParser.QHandler#parseDetails(com.theminequest.MineQuest.Quest.Quest, java.util.List)
	 * GroupLimit:3
	 */
	@Override
	public void parseDetails(Quest q, List<String> line) {
		q.groupLimit = Integer.parseInt(line.get(0));
	}

}
