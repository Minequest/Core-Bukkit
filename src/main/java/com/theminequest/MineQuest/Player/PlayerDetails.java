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
import com.theminequest.MineQuest.BukkitEvents.PlayerLevelEvent;
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
	private long mana;

	public PlayerDetails(Player p) {
		quest = -1;
		team = -1;
		player = p;
		abilitiesEnabled = false;
		abilitiesCoolDown = new LinkedHashMap<Ability,Long>();
		// check for player existence in DB.
		// if player does not, add.
		
		// get level from SQL;
		int level = 0;
		// give the player almost full mana (3/4 full)
		mana = (3/4)*(PlayerManager.BASE_MANA*level);
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
	
	public int getLevel(){
		// get level from sql; TODO STUB
		return 0;
	}
	
	public long getExperience(){
		// get experience from sql; TODO STUB
		return 0;
	}
	
	public long getMana(){
		return mana;
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
	
	public void levelUp(){
		// TODO STUB
		int currentlevel = 0;
		currentlevel+=1;
		PlayerLevelEvent event = new PlayerLevelEvent(player);
		Bukkit.getPluginManager().callEvent(event);
		// set experience to (PlayerManager.Base_EXP*newlevel)-currentexp;
	}
	
	public void modifyExperienceBy(int e){
		// TODO STUB
		long currentexp = 0;
		currentexp+=e;
		PlayerExperienceEvent event = new PlayerExperienceEvent(player, e);
		Bukkit.getPluginManager().callEvent(event);
		// set in SQL
		if (currentexp>=(PlayerManager.BASE_EXP*getLevel()))
			levelUp();
	}
	
	public void modifyManaBy(int m){
		int level = getLevel();
		long manatoadd = m;
		if (mana==PlayerManager.BASE_MANA*level)
			return;
		else if (m+mana>(PlayerManager.BASE_MANA*level))
			manatoadd = (PlayerManager.BASE_MANA*level)-(m+mana);
		mana+=manatoadd;
		PlayerManaEvent event = new PlayerManaEvent(player,m);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled())
			mana-=manatoadd;
	}

}
