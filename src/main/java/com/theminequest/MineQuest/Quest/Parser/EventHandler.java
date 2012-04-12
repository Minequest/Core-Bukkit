package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Quest.QuestParser.QHandler;

public class EventHandler implements QHandler {

	@Override
	public void parseDetails(Quest q, List<String> line) {
		int number = Integer.parseInt(line.get(0));
		// T = targeted event
		boolean targetedevent = false;
		if (line.get(1).equals("T")) {
			line.remove(1);
			targetedevent = true;
		}
		String eventname = line.get(1);
		String details = "";
		if (targetedevent)
			details += "T:";
		for (int i = 2; i < line.size(); i++) {
			details += line.get(i);
			if (i < line.size() - 1) {
				details += ":";
			}
		}
		//System.out.println(number + " : " + eventname + ":" + details);
		// final result: "eventname:T:details"
		q.events.put(number, new String(eventname + ":" + details));
	}

}
