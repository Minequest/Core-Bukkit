package com.theminequest.MineQuest.Utils;

import org.bukkit.entity.EntityType;

public class MobUtils {
	
	public static EntityType getEntityType(String s){
		return EntityType.fromName(s.toUpperCase());
	}

}
