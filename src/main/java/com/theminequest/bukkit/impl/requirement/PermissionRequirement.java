package com.theminequest.bukkit.impl.requirement;

import org.bukkit.Bukkit;

import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.requirement.QuestRequirement;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Requirement",
		ident = "PermissionRequirement",
		description = "Check that players have permissions.",
		arguments = { "Permission" },
		typeArguments = { DocArgType.STRING }
		)
public class PermissionRequirement extends QuestRequirement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1456690606555464696L;
	private String permission;
	
	@Override
	public void parseDetails(String[] details) {
		permission = details[0];
	}
	
	@Override
	public boolean isSatisfied(QuestDetails details, MQPlayer player) {
		return Bukkit.getPlayerExact(player.getName()).hasPermission(permission);
	}
	
}
