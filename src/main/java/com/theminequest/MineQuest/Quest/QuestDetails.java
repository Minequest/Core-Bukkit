/*
 * This file is part of MineQuest, The ultimate MMORPG plugin!.
 * MineQuest is licensed under GNU General Public License v3.
 * Copyright (C) 2012 The MineQuest Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.theminequest.MineQuest.Quest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.theminequest.MineQuest.I18NMessage;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Edit.Edit;
import com.theminequest.MineQuest.API.Requirements.QuestRequirement;
import com.theminequest.MineQuest.API.Target.TargetDetails;

/**
 * Represents a QuestDetails and associated properties
 *
 */
public class QuestDetails implements com.theminequest.MineQuest.API.Quest.QuestDetails {
	
	public static final String ICOMPLETE = "DETAILSCOMPLETE";
	public static final String IFAILED = "DETAILSFAILED";
	public static final String IERROR = "DETAILSERROR";
	public static final String IABORT = "DETAILSABORT";
	public static final String IACCEPT = "DETAILSACCEPT";
	public static final String INOEDIT = "DETAILSNOEDIT";
	public static final String INODESC = "DETAILSNODESC";

	/**
	 * 
	 */
	private static final long serialVersionUID = 9148896666724411547L;
	private Map<String, Serializable> database;

	/**
	 * Create a QuestDetails from the file
	 * @param f Valid quest file to read
	 * @throws IOException  
	 */
	protected QuestDetails(File f) throws IOException {
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
		setProperty(QuestDetails.QUEST_DESCRIPTION,I18NMessage.getLocale().getString(INODESC));
		setProperty(QuestDetails.QUEST_ACCEPT,I18NMessage.getLocale().getString(IACCEPT));
		setProperty(QuestDetails.QUEST_ABORT,I18NMessage.getLocale().getString(IABORT));
		setProperty(QuestDetails.QUEST_COMPLETE,I18NMessage.getLocale().getString(ICOMPLETE));
		setProperty(QuestDetails.QUEST_FAIL,I18NMessage.getLocale().getString(IFAILED));
		setProperty(QuestDetails.QUEST_SPAWNRESET,true);

		double[] spawnPoint = new double[3];
		spawnPoint[0] = 0;
		spawnPoint[1] = 64;
		spawnPoint[2] = 0;
		setProperty(QuestDetails.QUEST_SPAWNPOINT,spawnPoint);

		setProperty(QuestDetails.QUEST_EDITMESSAGE,ChatColor.GRAY + I18NMessage.getLocale().getString(INOEDIT));
		setProperty(QuestDetails.QUEST_WORLD,Bukkit.getWorlds().get(0).getName());
		setProperty(QuestDetails.QUEST_LOADWORLD,false);
		
		setProperty(QuestDetails.QUEST_TASKS,new LinkedHashMap<Integer,Integer[]>(0));
		setProperty(QuestDetails.QUEST_EVENTS,new LinkedHashMap<Integer, String>(0));
		setProperty(QuestDetails.QUEST_TARGETS,new LinkedHashMap<Integer, TargetDetails>(0));
		setProperty(QuestDetails.QUEST_EDITS,new LinkedHashMap<Integer,Edit>(0));

		setProperty(QuestDetails.QUEST_NETHERWORLD,false);
		setProperty(QuestDetails.QUEST_REQUIREMENTDETAILS,new LinkedHashMap<Integer,QuestRequirement>());
		setProperty(QuestDetails.QUEST_GETREQUIREMENTS, new LinkedList<Integer>());
		setProperty(QuestDetails.QUEST_STARTREQUIREMENTS, new LinkedList<Integer>());
	}
	
	
	/**
	 * Reads in the file description from the quest folder.
	 * @param p 
	 * @throws IOException  
	 */
	private void readInFile() throws IOException {
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

	@SuppressWarnings("unchecked")
	@Override
	public <E> E setProperty(String key, Serializable property) {
		return (E) database.put(key, property);
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
