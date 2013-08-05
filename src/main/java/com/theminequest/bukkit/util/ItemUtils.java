package com.theminequest.bukkit.util;

import org.bukkit.Material;

public class ItemUtils {
	public static Material getMaterial(String s) {
		Material m = Material.matchMaterial(s);
		if (m == null) {
			try {
				m = Material.getMaterial(Integer.valueOf(s));
			} catch (NumberFormatException e) {}
		}
		return m;
	}
}
