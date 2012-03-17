package com.theminequest.MineQuest.Player;

import java.sql.ResultSet;
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
import com.theminequest.MineQuest.BukkitEvents.PlayerExperienceEvent;
import com.theminequest.MineQuest.BukkitEvents.PlayerManaEvent;

/**
 * Extra details about the Player
 * 
 * @author MineQuest
 * 
 */
public class PlayerDetails {

	private long quest;
	private long team;
	private Player player;
	private boolean abilitiesEnabled;
	// >_>
	public LinkedHashMap<Ability,Long> abilitiesCoolDown;
	// player properties
	private long experience;
	private long mana;

	public PlayerDetails(Player p) {
		quest = -1;
		team = -1;
		player = p;
		abilitiesEnabled = false;
		abilitiesCoolDown = new LinkedHashMap<Ability,Long>();
		// check for player existence in DB.
		// if player does not, add.
		
		// now get player Experience
		
		// and now get player Mana
		
		// and feel happeh.
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
		// TODO STUB
		long currentexp = 0;
		currentexp+=e;
		PlayerExperienceEvent event = new PlayerExperienceEvent(player, e);
		Bukkit.getPluginManager().callEvent(event);
	}
	
	public void modifyManaBy(int mana){
		// TODO STUB
		long currentmana = 0;
		currentmana+=mana;
		PlayerManaEvent event = new PlayerManaEvent(player,mana);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled())
			currentmana-=mana;
		
	}

}
