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
