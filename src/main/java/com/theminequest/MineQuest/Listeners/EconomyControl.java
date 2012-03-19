/**
 * This file, EconomyControl.java, is part of MineQuest:
 * A full featured and customizable quest/mission system.
 * Copyright (C) 2012 The MineQuest Team
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 **/
package com.theminequest.MineQuest.Listeners;

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
