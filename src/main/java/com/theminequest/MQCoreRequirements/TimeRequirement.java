package com.theminequest.MQCoreRequirements;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.Requirements.QuestRequirement;
import com.theminequest.MineQuest.API.Utils.TimeUtils;

public class TimeRequirement extends QuestRequirement {

	private long lowertick;
	private long uppertick;
	
	@Override
	public void parseDetails(String[] details) {
		lowertick = TimeUtils.matchTime(details[0]);
		uppertick = TimeUtils.matchTime(details[1]);
	}

	@Override
	public boolean isSatisfied(Player player) {

		long worldtime = player.getLocation().getWorld().getTime();
		if (worldtime>=lowertick && worldtime <= uppertick)
			return true;
		else if (uppertick < lowertick) {
			if (worldtime >= lowertick || worldtime <= uppertick)
				return true;
		}
		return false;
	}
	
}
