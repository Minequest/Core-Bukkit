package com.theminequest.MineQuest.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.PropertiesFile;
import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.AbilityAPI.Ability;

import com.theminequest.MineQuest.PlayerEvent.ExpEvent;

/**
 * Extra details about the Player
 * 
 * @author MineQuest
 * 
 */
public class PlayerDetails {

	private long quest;
	private long team;
	private PropertiesFile playerspecs;
	private Player player;
	private boolean abilitiesEnabled;
	// >_>
	public LinkedHashMap<Ability,Long> abilitiesCoolDown;

	public PlayerDetails(Player p) {
		quest = -1;
		team = -1;
		player = p;
		abilitiesEnabled = false;
		abilitiesCoolDown = new LinkedHashMap<Ability,Long>();
		// get all these details...
		playerspecs = new PropertiesFile(Bukkit.getPluginManager()
				.getPlugin("MineQuest").getDataFolder()
				+ "/players/" + p.getName() + ".properties");
	}
	
	public long getQuest(){
		return quest;
	}
	
	public void setQuest(long q){
		quest = q;
	}
	
	public long getTeam(){
		return team;
	}
	
	public void setTeam(long t){
		team = t;
	}
	
	public void save(){
		
	}
	
	/*
	 * A user should be able to toggle ability use on/off
	 * with a command, like /ability on/off?
	 */
	public boolean getAbilitiesEnabled(){
		return abilitiesEnabled;
	}
	
	public void setAbilitiesEnabled(boolean b){
		abilitiesEnabled = b;
	}
	
	public void modifyExperienceBy(int e){
		long currentexp = playerspecs.getLong("experience");
		currentexp+=e;
		playerspecs.setLong("experience", currentexp);
		playerspecs.save();
		ExpEvent event = new ExpEvent(player, e);
		Bukkit.getPluginManager().callEvent(event);
	}
	
	public void modifyManaBy(int mana){
		long currentmana = playerspecs.getLong("mana");
		currentmana+=mana;
	}

}
