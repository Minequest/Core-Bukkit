package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Quest.QuestDescription;
import com.theminequest.MineQuest.Quest.QuestParser.QHandler;

public class AreaPreserveHandler implements QHandler {

	@Override
	public void parseDetails(QuestDescription q, List<String> line) {
		if (!line.get(0).equals(""))
			q.areaPreserve[0] = Double.parseDouble(line.get(0));
		if (!line.get(1).equals(""))
			q.areaPreserve[1] = Double.parseDouble(line.get(1));
		if (!line.get(2).equals(""))
			q.areaPreserve[2] = Double.parseDouble(line.get(2));
		if (!line.get(3).equals(""))
			q.areaPreserve[3] = Double.parseDouble(line.get(3));
		if (!line.get(4).equals(""))
			q.areaPreserve[4] = Double.parseDouble(line.get(4));
		if (!line.get(5).equals(""))
			q.areaPreserve[5] = Double.parseDouble(line.get(5));
	}

}
