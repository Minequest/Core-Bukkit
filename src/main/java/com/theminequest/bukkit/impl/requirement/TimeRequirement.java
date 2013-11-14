package com.theminequest.bukkit.impl.requirement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.requirement.QuestRequirement;
import com.theminequest.bukkit.util.TimeUtils;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Requirement",
		ident = "TimeRequirement",
		description = "Check the time of the world.",
		arguments = { "Lower bound time", "Upper bound time" },
		typeArguments = { DocArgType.INT, DocArgType.INT }
		)
public class TimeRequirement extends QuestRequirement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3941243788802597857L;
	private long lowertick;
	private long uppertick;
	
	@Override
	public void parseDetails(String[] details) {
		lowertick = TimeUtils.matchTime(details[0]);
		uppertick = TimeUtils.matchTime(details[1]);
	}
	
	@Override
	public boolean isSatisfied(QuestDetails details, MQPlayer p) {
		
		Player player = Bukkit.getPlayerExact(p.getName());
		
		long worldtime = player.getLocation().getWorld().getTime();
		if ((worldtime >= lowertick) && (worldtime <= uppertick))
			return true;
		else if (uppertick < lowertick)
			if ((worldtime >= lowertick) || (worldtime <= uppertick))
				return true;
		return false;
	}
	
}
