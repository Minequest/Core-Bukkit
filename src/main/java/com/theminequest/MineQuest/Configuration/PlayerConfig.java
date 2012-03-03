package com.theminequest.MineQuest.Configuration;

import java.io.File;
import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.DatabaseHandler;
import lib.PatPeter.SQLibrary.MySQL;
import lib.PatPeter.SQLibrary.SQLite;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Player.PlayerDetails;

public class PlayerConfig {

	private MineQuest minequest;
	
	public PlayerConfig(MineQuest mq){
		minequest = mq;
	}
	
	public PlayerDetails getPlayerDetails(Player p){
		
	}
	
}
