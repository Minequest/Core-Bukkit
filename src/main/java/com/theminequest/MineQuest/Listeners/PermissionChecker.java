package com.theminequest.MineQuest.Listeners;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;

public class PermissionChecker {
	public boolean permissionCheck;
	
	public boolean isMQPlayer(Player player){
		permissionCheck = MineQuest.permission.playerHas(player.getWorld(), player.getName(), "MineQuest.Quester");
		if (permissionCheck = false) {
				return false;
		}
		return true;
	}
	public boolean isMQAdmin(Player player){
		permissionCheck = MineQuest.permission.playerHas(player.getWorld(), player.getName(), "MineQuest.Admin");
		if (permissionCheck = false){
			return false;
		}
		return true;
	}
	
	public boolean isMQMod(Player player){
		permissionCheck = MineQuest.permission.playerHas(player.getWorld(), player.getName(), "MineQuest.Mod");
		if (permissionCheck = false){
			return false;
		}
		return true;
	}
	
}
