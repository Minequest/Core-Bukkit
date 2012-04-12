package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import org.bukkit.ChatColor;

import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Quest.QuestParser.QHandler;

public class EditMessageHandler implements QHandler {

	@Override
	public void parseDetails(Quest q, List<String> line) {
		q.editMessage = ChatColor.GRAY + line.get(0);
	}

}
