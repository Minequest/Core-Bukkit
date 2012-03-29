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
import com.theminequest.MineQuest.Backend.BackendFailedException.Reason;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.BukkitEvents.QuestAvailableEvent;
import com.theminequest.MineQuest.Group.Team;

public final class QuestBackend {

	/**
	 * Mark a quest as AVAILABLE for the player.
	 * @param p Player
	 * @param quest_name Quest Name
	 * @throws BackendFailedException If the backend failed to do its job.
	 */
	public static void giveQuestToPlayer(Player p, String quest_name)
			throws BackendFailedException {
		boolean repeatable = false;
		boolean regive = false;
		try {
			repeatable = isRepeatable(quest_name);
		} catch (IllegalArgumentException e){
			throw new BackendFailedException(e);
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
			throw new BackendFailedException(Reason.SQL,e);
		}
		for (String s : noncompletedquests){
			if (quest_name.equalsIgnoreCase(s))
				throw new BackendFailedException(Reason.ALREADYHAVEQUEST);
		}

		// if not repeatable, check if already completed
			List<String> completedquests;
			try {
				completedquests = getQuests(QuestAvailability.COMPLETED,p);
			} catch (SQLException e) {
				MineQuest.log(Level.SEVERE, "[QuestBackend] Invoked giveQuestToPlayer by " +
						p.getName() + " on quest " + quest_name + " threw exception:");
				MineQuest.log(Level.SEVERE, e.toString());
				throw new BackendFailedException(Reason.SQL,e);
			}
			for (String s : completedquests){
				if (quest_name.equalsIgnoreCase(s)){
					if (repeatable)
						regive = true;
					else
						throw new BackendFailedException(Reason.UNREPEATABLEQUEST);
				}
			}

		if (!regive)
			MineQuest.sqlstorage.querySQL("Quests/giveQuest", p.getName(), quest_name);
		else
			MineQuest.sqlstorage.querySQL("Quests/reGiveQuest", p.getName(), quest_name);
		QuestAvailableEvent event = new QuestAvailableEvent(quest_name, p);
		Bukkit.getPluginManager().callEvent(event);
	}

	/**
	 * Get quests that the player has
	 * @param type State of the quests (available, accepted, completed)
	 * @param p Player
	 * @return List of quest names
	 * @throws SQLException if the backend had trouble querying.
	 */
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

	/**
	 * This checks if a quest file exists; and also if it's repeatable.<br>
	 * <b>This has been marked for resignature when the quest file format changes.</b>
	 * Frontends may have to adjust their method calls.
	 * @param quest_name Quest to check
	 * @return true if repeatable
	 */
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
	
	/**
	 * Accept a quest
	 * @param p Player
	 * @param quest_name Quest Name
	 * @throws BackendFailedException if the player doesn't have the quest
	 */
	public static void acceptQuest(Player p, String quest_name) throws BackendFailedException{
		try {
			List<String> nonacceptedquests = getQuests(QuestAvailability.AVAILABLE, p);
			for (String s : nonacceptedquests){
				if (s.equalsIgnoreCase(quest_name)){
					MineQuest.sqlstorage.querySQL("Quests/acceptQuest", p.getName(), quest_name);
					return;
				}
			}
			throw new BackendFailedException(Reason.NOTHAVEQUEST);
		} catch (SQLException e) {
			throw new BackendFailedException(e);
		}
	}
	
	/**
	 * Start a quest
	 * @param p <b>LEADER</b> of the team
	 * @param name Quest Name
	 * @throws BackendFailedException If quest couldn't be started
	 */
	/* FIXME NOW DELEGATED TO GROUPBACKEND */
/*	public static void startQuest(Player p, String name) throws BackendFailedException {
		long teamid = MineQuest.
		if (teamid==-1){
			teamid = MineQuest.groupManager.createTeam(p);
		}
		Team t = MineQuest.groupManager.getGroup(teamid);
		Player leader = t.getLeader();
		if (leader!=p)
			throw new BackendFailedException("Only leaders can start quests!");
		try {
			List<String> possiblequests = getQuests(QuestAvailability.ACCEPTED, leader);
			for (String s : possiblequests){
				if (s.equalsIgnoreCase(name)){
					MineQuest.questManager.getQuest(MineQuest.questManager.startQuest(s, t)).enterQuest();
				}
			}
			throw new BackendFailedException("No such *accepted* quest!");
		} catch (SQLException e) {
			throw new BackendFailedException(e);
		}
	}*/
	
	/**
	 * Cancel the active quest (abandon quest)
	 * @param p <b>LEADER</b> of the team that wants to abandon quest
	 * @throws BackendFailedException
	 */
	/* FIXME NOW DELEGATED TO GROUPBACKEND */
/*	public static void cancelActiveQuest(Player p) throws BackendFailedException {
		long teamid = MineQuest.playerManager.getPlayerDetails(p).getTeam();
		if (teamid==-1){
			throw new BackendFailedException("You're not on a team!");
		}
		Team t = MineQuest.groupManager.getGroup(teamid);
		Player leader = t.getLeader();
		if (leader!=p)
			throw new BackendFailedException("Only leaders can cancel the current quest!");
		long questid = MineQuest.playerManager.getPlayerDetails(leader).getQuest();
		if (questid==-1)
			throw new BackendFailedException("Not on a quest!");
		MineQuest.questManager.getQuest(questid).finishQuest(CompleteStatus.CANCELED);
	}*/

}
