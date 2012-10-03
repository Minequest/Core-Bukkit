package com.theminequest.MQCoreRequirements;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.Requirements.QuestRequirement;

public class RealDateRequirement extends QuestRequirement{
	
	private Date before;
	private Date after;
	
	@Override
	public void parseDetails(String[] details) {
		try {
			before = DateFormat.getInstance().parse(details[0]);
			after = DateFormat.getInstance().parse(details[1]);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public boolean isSatisfied(Player player) {
		Date current = Calendar.getInstance().getTime();
		return current.after(before) && current.before(after);
	}
	
}
