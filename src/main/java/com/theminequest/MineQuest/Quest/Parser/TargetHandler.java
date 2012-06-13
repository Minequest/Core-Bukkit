package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;
import java.util.Map;

import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;
import com.theminequest.MineQuest.API.Target.TargetDetails;
import static com.theminequest.MineQuest.API.Quest.QuestDetails.*;
import com.theminequest.MineQuest.API.Quest.QuestDetails;

public class TargetHandler implements QHandler {

	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		int number = Integer.parseInt(line.get(0));
		String d = "";
		for (int i=1; i<line.size(); i++){
			d += line.get(i);
			if (i!=line.size()-1)
				d+=":";
		}
		Map<Integer,TargetDetails> targets = q.getProperty(QUEST_TARGETS);
		targets.put(number, new TargetDetails(d));
	}

}
