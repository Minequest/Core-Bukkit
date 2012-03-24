package com.theminequest.MineQuest.Backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.QuestAvailableEvent;

public final class QuestBackend {

	public static void giveQuestToPlayer(Player p, String quest_name)
			throws IllegalArgumentException {
		boolean repeatable = false;
		try {
			repeatable = isRepeatable(quest_name);
		} catch (IllegalArgumentException e){
			throw e;
		}
		// check if the player already has this quest
		List<String> noncompletedquests;
		try {
			noncompletedquests = getQuests(QuestAvailability.ACCEPTED,p);
			noncompletedquests.addAll(getQuests(QuestAvailability.AVAILABLE,p));
		} catch (SQLException e) {
			MineQuest.log(Level.SEVERE, "[QuestBackend] Invoked giveQuestToPlayer by " +
					p.getName() + " on quest " + quest_name + " threw exception:");
			MineQuest.log(Level.SEVERE, e.toString());
			throw new IllegalArgumentException("Oh no! Something went wrong...",e);
		}
		for (String s : noncompletedquests){
			if (quest_name.equalsIgnoreCase(s))
				throw new IllegalArgumentException("You already have this quest pending!");
		}

		// if not repeatable, check if already completed
		if (!repeatable){
			List<String> completedquests;
			try {
				completedquests = getQuests(QuestAvailability.COMPLETED,p);
			} catch (SQLException e) {
				MineQuest.log(Level.SEVERE, "[QuestBackend] Invoked giveQuestToPlayer by " +
						p.getName() + " on quest " + quest_name + " threw exception:");
				MineQuest.log(Level.SEVERE, e.toString());
				throw new IllegalArgumentException("Oh no! Something went wrong...",e);
			}
			for (String s : completedquests){
				if (quest_name.equalsIgnoreCase(s)){
					throw new IllegalArgumentException("This quest is not repeatable!");
				}
			}
		}

		MineQuest.sqlstorage.querySQL("Quests/giveQuest", p.getName(),
				quest_name);
		QuestAvailableEvent event = new QuestAvailableEvent(quest_name, p);
		Bukkit.getPluginManager().callEvent(event);
	}

	public static List<String> getQuests(QuestAvailability type, Player p) throws SQLException{
		ResultSet currentquests;
		if (type == QuestAvailability.ACCEPTED)
			currentquests = MineQuest.sqlstorage.querySQL(
					"Quests/getPlayerQuestsNotCompleted", p.getName());
		else if (type == QuestAvailability.COMPLETED)
			currentquests = MineQuest.sqlstorage.querySQL(
					"Quests/getPlayerQuestsCompleted", p.getName());
		else
			currentquests = MineQuest.sqlstorage.querySQL(
					"Quests/getPlayerQuestsAvailable", p.getName());
		List<String> quests = new ArrayList<String>();
		if (!currentquests.first())
			return quests;
		do {
			quests.add(currentquests.getString("Q_ID"));
		}while (currentquests.next());
		return quests;
	}

	public static boolean isRepeatable(String quest_name){
		File qfile = new File(MineQuest.activePlugin.getDataFolder()
				+ File.separator + "quests" + File.separator + quest_name
				+ ".quest");
		Scanner qfs;
		try {
			qfs = new Scanner(qfile);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("[QuestBackend] No such quest!",e);
		}
		while(qfs.hasNextLine()){
			String[] examine = qfs.nextLine().split(":");
			if (examine.length!=2)
				continue;
			if (examine[0].equalsIgnoreCase("Repeatable")){
				return Boolean.parseBoolean(examine[1]);
			}
		}
		return true;
	}

}
