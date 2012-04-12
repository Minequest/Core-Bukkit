package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Quest.QuestParser.QHandler;
import com.theminequest.MineQuest.Target.TargetDetails;

public class TargetHandler implements QHandler {

	@Override
	public void parseDetails(Quest q, List<String> line) {
		int number = Integer.parseInt(line.get(0));
		String d = "";
		for (int i=1; i<line.size(); i++){
			d += line.get(i);
			if (i!=line.size()-1)
				d+=":";
		}
		q.targets.put(number, new TargetDetails(q.questid,d));
	}

}
