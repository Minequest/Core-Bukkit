package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;
import static com.theminequest.MineQuest.API.Quest.QuestDetails.*;
import com.theminequest.MineQuest.API.Quest.QuestDetails;

public class SpawnHandler implements QHandler {

	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		double[] spawnPoint = q.getProperty(QUEST_SPAWNPOINT);
		if (!line.get(0).equals(""))
			spawnPoint[0] = Double.parseDouble(line.get(0));
		if (!line.get(1).equals(""))
			spawnPoint[1] = Double.parseDouble(line.get(1));
		if (!line.get(2).equals(""))
			spawnPoint[2] = Double.parseDouble(line.get(2));
	}

}
