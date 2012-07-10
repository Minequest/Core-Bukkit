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
package com.theminequest.MineQuest;

import org.bukkit.entity.Player;

public enum Permissions {
	
	ADMINRELOAD("admin.command.reload"),
	QUESTCOMMAND("user.command.quest"),
	PARTYCOMMAND("user.command.party"),
	STARTQUEST("user.quest.start"),
	LEADQUEST("user.quest.leader"),
	CREATEPARTY("user.party.create"),
	JOINPARTY("user.party.join"),
	INVITEPARTY("user.party.invite");
	
	private String node;
	
	private Permissions(String value){
		if (value==null || value.contains("*") || value.isEmpty())
			throw new IllegalArgumentException("Use a valid node!");
		node = "minequest." + value;
	}
	
	public boolean hasPermission(Player player){
		if (player.isOp())
			return true;
		String[] parts = node.split(".");
		for (int i=0; i<parts.length; i++){
			String newnode = "";
			for (int j=0; j<i+1; i++){
				newnode+=parts[j] + ".";
			}
			if (i!=parts.length-1)
				newnode+="*";
			else
				newnode = newnode.substring(0, newnode.length()-1);
			if (player.hasPermission(node))
				return true;
			if (MineQuest.permission.has(player, node))
				return true;
		}
		
		return false;
	}
	
	public String getValue(){
		return node;
	}
	
	public String toString(){
		return node;
	}

}
