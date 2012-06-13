package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;
import com.theminequest.MineQuest.API.Quest.QuestRequirement;
import com.theminequest.MineQuest.Quest.RequirementFactory;

import static com.theminequest.MineQuest.API.Quest.QuestDetails.*;

public class RequirementHandler implements QHandler {

	/*
	 * Requirement:TYPE:details
	 */
	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		List<QuestRequirement> r = q.getProperty(QUEST_REQUIREMENTS);
		String details = "";
		for (int i=1; i<line.size(); i++){
			details+=line.get(i) + ":";
		}
		if (details.length()!=0)
			details = details.substring(0, details.length()-1);
		QuestRequirement qr = RequirementFactory.constructRequirement(line.get(0), q, details);
		if (qr!=null)
			r.add(qr);
	}

}
