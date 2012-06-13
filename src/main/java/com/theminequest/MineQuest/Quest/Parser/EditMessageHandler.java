package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import org.bukkit.ChatColor;

import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestParser.QHandler;
import com.theminequest.MineQuest.Quest.Quest;
import static com.theminequest.MineQuest.API.Quest.QuestDetails.*;

public class EditMessageHandler implements QHandler {

	@Override
	public void parseDetails(QuestDetails q, List<String> line) {
		q.setProperty(QUEST_EDITMESSAGE, ChatColor.GRAY + line.get(0));
	}

}
