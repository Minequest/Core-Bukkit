package com.theminequest.bukkit.platform;

import org.bukkit.Location;

import com.theminequest.api.platform.MQLocation;

public class BukkitLocation extends MQLocation {
	
	public BukkitLocation(Location loc) {
		super(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ());
	}
	
}
