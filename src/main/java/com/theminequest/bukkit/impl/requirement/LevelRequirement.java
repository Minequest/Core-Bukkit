package com.theminequest.bukkit.impl.requirement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.theminequest.api.platform.MQPlayer;
import com.theminequest.api.requirement.QuestRequirement;

public class LevelRequirement extends QuestRequirement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3997477758544440822L;
	private int level;
	private Comparison compare;
	
	private enum Comparison {
		EQUALS, LESS, LESSEQ, GREATER, GREATEREQ;
	}
	
	@Override
	public void parseDetails(String[] details) {
		level = Integer.parseInt(details[0]);
		if (details[1].equals("<"))
			compare = Comparison.LESS;
		else if (details[1].equals("<="))
			compare = Comparison.LESSEQ;
		else if (details[1].equals(">"))
			compare = Comparison.GREATER;
		else if (details[1].equals(">="))
			compare = Comparison.GREATEREQ;
		else
			compare = Comparison.EQUALS;
	}
	
	@Override
	public boolean isSatisfied(MQPlayer player) {
		Player p = Bukkit.getPlayerExact(player.getName());
		int playerLvl = p.getLevel();
		switch (compare) {
		case LESS:
			return level < playerLvl;
		case LESSEQ:
			return level <= playerLvl;
		case GREATER:
			return level > playerLvl;
		case GREATEREQ:
			return level >= playerLvl;
		default:
			return level == playerLvl;
		}
	}
	
}
