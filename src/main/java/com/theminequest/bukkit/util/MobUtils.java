package com.theminequest.bukkit.util;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;

public class MobUtils {
	
	public static EntityType getEntityType(String s) {
		if (s == null)
			return null;
		
		s = s.trim();
		
		EntityType ret = EntityType.fromName(s);
		if (ret == null) {
			try {
				ret = EntityType.valueOf(s.toUpperCase());
			} catch (IllegalArgumentException e) {
			}
		}
		if (ret == null) {
			try {
				ret = EntityType.fromId(Integer.parseInt(s));
			} catch (NumberFormatException e) {
			}
		}
		
		return ret != null && ret.isAlive() ? ret : null;
	}
	
	public static LivingEntity addlProps(LivingEntity e) {
		EntityType entityType = e.getType();
		if (entityType == EntityType.SKELETON && ((Skeleton) e).getSkeletonType().getId() == 1) {
			((Skeleton) e).getEquipment().setItemInHand(new ItemStack(Material.STONE_SWORD));
		} else if (entityType == EntityType.SKELETON && ((Skeleton) e).getSkeletonType().getId() == 0) {
			((Skeleton) e).getEquipment().setItemInHand(new ItemStack(Material.BOW));
		}
		return e;
	}
	
}
