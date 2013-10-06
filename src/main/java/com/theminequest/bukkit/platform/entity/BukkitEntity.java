package com.theminequest.bukkit.platform.entity;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import com.theminequest.api.Managers;
import com.theminequest.api.platform.MQLocation;
import com.theminequest.api.platform.entity.MQEntity;
import com.theminequest.bukkit.platform.BukkitLocation;

public class BukkitEntity implements MQEntity {
	
	private Entity entity;
	
	public BukkitEntity(Entity entity) {
		this.entity = entity;
	}
	
	public Entity getBukkitEntity() {
		return entity;
	}
	
	@Override
	public MQLocation getLocation() {
		return new BukkitLocation(entity.getLocation());
	}
	
	@Override
	public void teleport(MQLocation location) {
		entity.teleport((Location) Managers.getPlatform().fromLocation(location));
	}
	
	@Override
	public void remove() {
		entity.remove();
	}
	
	@Override
	public long getEntityId() {
		return entity.getEntityId();
	}
	
	@Override
	public UUID getUUID() {
		return entity.getUniqueId();
	}
	
}
