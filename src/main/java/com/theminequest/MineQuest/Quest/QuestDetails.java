package com.theminequest.MineQuest.Quest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.theminequest.MineQuest.I18NMessage;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Edit.Edit;
import com.theminequest.MineQuest.API.Quest.QuestRequirement;
import com.theminequest.MineQuest.API.Target.TargetDetails;

/**
 * Represents a QuestDetails and associated properties
 * @author Robert
 *
 */
public class QuestDetails implements com.theminequest.MineQuest.API.Quest.QuestDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9148896666724411547L;
	public Map<String, Serializable> database;

	/**
	 * Create a QuestDetails from the file
	 * @param f Valid quest file to read
	 * @throws FileNotFoundException If file was suddenly disappears
	 */
	protected QuestDetails(File f) throws FileNotFoundException{
		String id = f.getName().substring(0, f.getName().indexOf(".quest"));
		database = Collections.synchronizedMap(new LinkedHashMap<String,Serializable>());
		setProperty(QuestDetails.QUEST_FILE,f);
		setDefaults(id);
		readInFile();
	}
	
	/**
	 * Sets the defaults. In particular, this initializes default
	 * objects, sets default names, locales, and spawn points if
	 * the quest file does not use them.
	 * @param id Quest Name Identification
	 */
	private void setDefaults(String id){
		setProperty(QuestDetails.QUEST_NAME,id);
		// DEFAULTS start
		setProperty(QuestDetails.QUEST_DISPLAYNAME,id);
		setProperty(QuestDetails.QUEST_DESCRIPTION,I18NMessage.Quest_NODESC.getDescription());
		setProperty(QuestDetails.QUEST_ACCEPT,I18NMessage.Quest_ACCEPT.getDescription());
		setProperty(QuestDetails.QUEST_ABORT,I18NMessage.Quest_ABORT.getDescription());
		setProperty(QuestDetails.QUEST_COMPLETE,I18NMessage.Quest_COMPLETE.getDescription());
		setProperty(QuestDetails.QUEST_SPAWNRESET,true);

		double[] spawnPoint = new double[3];
		spawnPoint[0] = 0;
		spawnPoint[1] = 64;
		spawnPoint[2] = 0;
		setProperty(QuestDetails.QUEST_SPAWNPOINT,spawnPoint);

		setProperty(QuestDetails.QUEST_EDITMESSAGE,ChatColor.GRAY + I18NMessage.Quest_NOEDIT.getDescription());
		setProperty(QuestDetails.QUEST_WORLD,Bukkit.getWorlds().get(0).getName());
		setProperty(QuestDetails.QUEST_LOADWORLD,false);
		
		setProperty(QuestDetails.QUEST_TASKS,new LinkedHashMap<Integer,Integer[]>(0));
		setProperty(QuestDetails.QUEST_EVENTS,new LinkedHashMap<Integer, String>(0));
		setProperty(QuestDetails.QUEST_TARGETS,new LinkedHashMap<Integer, TargetDetails>(0));
		setProperty(QuestDetails.QUEST_EDITS,new LinkedHashMap<Integer,Edit>(0));

		setProperty(QuestDetails.QUEST_NETHERWORLD,false);
		setProperty(QuestDetails.QUEST_REQUIREMENTS,new ArrayList<QuestRequirement>());
	}
	
	
	/**
	 * Reads in the file description from the quest folder.
	 * @param p 
	 * @throws FileNotFoundException If the file mysteriously disappeared.
	 */
	private void readInFile() throws FileNotFoundException{
		Managers.getQuestManager().getParser().parseDefinition(this);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof QuestDetails))
			return false;
		String ours = getProperty(QuestDetails.QUEST_NAME);
		String theirs = ((QuestDetails) obj).getProperty(QuestDetails.QUEST_NAME);
		return ours.equals(theirs);
	}

	@Override
	public int compareTo(com.theminequest.MineQuest.API.Quest.QuestDetails o) {
		String ours = getProperty(QuestDetails.QUEST_NAME);
		String theirs = o.getProperty(QuestDetails.QUEST_NAME);
		return ours.compareTo(theirs);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E getProperty(String key) {
		return (E) database.get(key);
	}

	@Override
	public String toString() {
		return database.toString();
	}

	@Override
	public void setProperty(String key, Serializable property) {
		database.put(key, property);
	}

	@Override
	public boolean containsProperty(String key) {
		return database.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E removeProperty(String key) {
		return (E) database.remove(key);
	}


}
