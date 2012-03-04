package com.theminequest.MineQuest.QuestSign;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class QuestSign {
	
	public static boolean signCheck(Block block){
		if (block.getState() instanceof Sign == true){
				return true;
			}
		return false;
	}
	public static boolean isQuestSign(Sign sign){
		String[] line = sign.getLines();
		if (line[1] != null && (line[2].contentEquals("[Quest]"))){
			return true;
		}
		return false;
	}
	public static String questName(Sign sign){
		String[] line = sign.getLines();
		String questName = line[2].toString();
		return questName;
		
	}
}