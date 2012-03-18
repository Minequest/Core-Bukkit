package com.theminequest.MineQuest.Player;

import java.sql.SQLException;
import java.util.LinkedHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.theminequest.MineQuest.MineQuest;

public class PlayerManager implements Listener {
	
	public static final int BASE_EXP = 100;
	public static final int BASE_MANA = 100;

	private LinkedHashMap<Player,PlayerDetails> players;
	
	public PlayerManager(){
		 players = new LinkedHashMap<Player,PlayerDetails>();
		 // mana +1 every 5 seconds.
		 Bukkit.getScheduler().scheduleSyncRepeatingTask(MineQuest.activePlugin, new Runnable(){

			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()){
					MineQuest.playerManager.getPlayerDetails(p).modifyManaBy(1);
				}
			}
			 
		 }, 300, 100);
		 
		 Bukkit.getScheduler().scheduleSyncRepeatingTask(MineQuest.activePlugin, new Runnable(){

			@Override
			public void run() {
				saveAll();
				MineQuest.log("Saved all PlayerDetails.");
			}
			 
		 }, 1200, 18000);
	}
	
	private void saveAll(){
		for (PlayerDetails d : players.values())
			d.save();
	}
	
	private void playerAcct(Player p){
		if (!players.containsKey(p)){
			try {
				players.put(p,new PlayerDetails(p));
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public PlayerDetails getPlayerDetails(Player p){
		playerAcct(p);
		return players.get(p);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		playerAcct(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		getPlayerDetails(e.getPlayer()).save();
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent e){
		getPlayerDetails(e.getPlayer()).save();
	}
	
}
