/**
 * This file, QuestManager.java, is part of MineQuest:
 * A full featured and customizable quest/mission system.
 * Copyright (C) 2012 The MineQuest Team
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 **/
package com.theminequest.MineQuest.Quest;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.QuestCompleteEvent;
import com.theminequest.MineQuest.BukkitEvents.QuestStartedEvent;
import com.theminequest.MineQuest.BukkitEvents.TaskCompleteEvent;
import com.theminequest.MineQuest.Group.Team;


public class QuestManager implements Listener {

	protected final String locationofQuests;
	private LinkedHashMap<Long,Quest> quests;
	private long questid;
	
	public QuestManager(){
		MineQuest.log("[Quest] Starting Manager...");
		quests = new LinkedHashMap<Long,Quest>();
		questid = 0;
		locationofQuests = MineQuest.configuration.questConfig
				.getString("questfolderlocation",
				MineQuest.activePlugin.getDataFolder().getAbsolutePath()
				+File.separator+"quests");
		File f = new File(locationofQuests);
		if (!f.exists() || !f.isDirectory()){
			f.delete();
			f.mkdirs();
		}
	}
	
	public long startQuest(String id){
		MineQuest.log(Level.WARNING, "4");
		quests.put(questid,new Quest(questid,id));
		long thisquestid = questid;
		questid++;
		MineQuest.log(Level.WARNING, "21");
		QuestStartedEvent e = new QuestStartedEvent(quests.get(thisquestid));
		Bukkit.getPluginManager().callEvent(e);
		MineQuest.log(Level.WARNING, "22");
		return thisquestid;
	}

	public Quest getQuest(long currentquest) {
		if (quests.containsKey(currentquest))
			return quests.get(currentquest);
		return null;
	}
	
	@EventHandler
	public void taskCompletion(TaskCompleteEvent e){
		getQuest(e.getQuestID()).onTaskCompletion(e);
	}
	
	@EventHandler
	public void onQuestCompletion(QuestCompleteEvent e){
		String questname = quests.get(e.getQuestId()).questname;
		for (Player p : e.getTeam().getPlayers()){
			MineQuest.sqlstorage.querySQL("Quests/completeQuest", p.getName(), questname);
		}
		quests.put(e.getQuestId(), null);
	}
	
}
