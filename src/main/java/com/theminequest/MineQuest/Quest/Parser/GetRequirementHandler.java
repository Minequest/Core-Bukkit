package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;
import static com.theminequest.MineQuest.API.Quest.QuestDetails.*;

public class GetRequirementHandler implements QHandler {
	
	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		List<Integer> getreqs = q.getProperty(QUEST_GETREQUIREMENTS);
		for (String l : line) {
			for (String s : l.split(","))
				getreqs.add(Integer.parseInt(s));
		}
	}
	
}
