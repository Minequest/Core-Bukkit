package com.theminequest.MineQuest.Player;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

public class PlayerManager {

	private static HashMap<Player,PlayerDetails> players = new HashMap<Player,PlayerDetails>();
	
	private static void playerAcct(Player p){
		if (!players.containsKey(p)){
			players.put(p,new PlayerDetails(p));
		}
	}
	
	public static PlayerDetails getPlayerDetails(Player p){
		playerAcct(p);
		return players.get(p);
	}
	
}
