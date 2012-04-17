package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Quest.QuestDescription;
import com.theminequest.MineQuest.Quest.QuestParser.QHandler;

public class SpawnHandler implements QHandler {

	@Override
	public void parseDetails(QuestDescription q, List<String> line) {
		if (!line.get(0).equals(""))
			q.spawnPoint[0] = Double.parseDouble(line.get(0));
		if (!line.get(1).equals(""))
			q.spawnPoint[1] = Double.parseDouble(line.get(1));
		if (!line.get(2).equals(""))
			q.spawnPoint[2] = Double.parseDouble(line.get(2));
	}

}
