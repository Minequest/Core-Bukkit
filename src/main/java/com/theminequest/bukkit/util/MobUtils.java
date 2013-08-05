package com.theminequest.bukkit.util;

import org.bukkit.entity.EntityType;

public class MobUtils {
	
	public static EntityType getEntityType(String s){
		if (s==null)
			return null;
		
		s = s.trim();
		
		EntityType ret = EntityType.fromName(s);
		if (ret == null) {
			try {
				ret = EntityType.valueOf(s.toUpperCase());
			} catch (IllegalArgumentException e) {}
		}
		if (ret == null) {
			try {
				ret = EntityType.fromId(Integer.parseInt(s));
			} catch (NumberFormatException e) {}
		}
		
		return ret != null && ret.isAlive() ? ret : null;
	}

}
