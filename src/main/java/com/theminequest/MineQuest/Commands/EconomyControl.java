package com.theminequest.MineQuest.Commands;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;

public class EconomyControl {
	private boolean possible;
	private String playerName;
	
//Checks current players balance to make sure they have enough money.
	private boolean hasEnough(Player player, double cost){
		playerName = player.getName();
		if(currentAmount(player) >= cost){
			return true;
		}
		return false;
	}
	
	private double currentAmount(Player player){
		return MineQuest.economy.getBalance(playerName);
	}
	
	private String playerName(Player player){
		return player.getName();
	}
	
	public boolean subtractAmount(Player player, double cost){
		possible = hasEnough(player, cost);
		if (possible = true){
			MineQuest.economy.bankWithdraw(playerName(player), cost);
			return true;
		}
		return false;
	}
	
	public void addAmount(Player player, double amount){
		MineQuest.economy.bankDeposit(player.getName(), amount);
	}
	
}
