package com.theminequest.bukkit.platform.entity;

import java.net.InetSocketAddress;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.theminequest.api.Managers;
import com.theminequest.api.platform.MQInventory;
import com.theminequest.api.platform.MQLocation;
import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.bukkit.platform.BukkitInventory;

public class BukkitPlayer implements MQPlayer {
	
	private int properties;
	private Player player;
	
	public BukkitPlayer(Player player) {
		this.player = player;
		this.properties = 0x00000000;
	}
	
	@Override
	public void chat(String message) {
		player.chat(message);
	}
	
	@Override
	public String getCustomName() {
		return player.getDisplayName();
	}
	
	@Override
	public String getName() {
		return player.getName();
	}
	
	@Override
	public void kick(String message) {
		player.kickPlayer(message);
	}
	
	@Override
	public boolean isOnline() {
		return player.isOnline();
	}
	
	@Override
	public InetSocketAddress getAddress() {
		return player.getAddress();
	}
	
	@Override
	public void sendMessage(String... message) {
		player.sendMessage(message);
	}
	
	@Override
	public void setCustomName(String name) {
		player.setDisplayName(name);
	}
	
	@Override
	public MQLocation getLocation() {
		return Managers.getPlatform().toLocation(player.getLocation());
	}
	
	@Override
	public void teleport(MQLocation location) {
		Location loc = Managers.getPlatform().fromLocation(location);
		player.teleport(loc);
	}
	
	@Override
	public MQInventory getInventory() {
		return new BukkitInventory(player.getInventory());
	}
	
	@Override
	public double getMaxHealth() {
		return player.getMaxHealth();
	}
	
	@Override
	public void setHealth(double health) {
		player.setHealth(health);
	}
	
	@Override
	public double getHealth() {
		return player.getHealth();
	}

	@Override
	public boolean equals(Object obj) {
		return player.getName().equals(((BukkitPlayer)obj).player.getName());
	}

	@Override
	public int hashCode() {
		return player.hashCode();
	}

	@Override
	public void remove() {
		player.remove();
	}

	@Override
	public long getEntityId() {
		return player.getEntityId();
	}

	@Override
	public UUID getUUID() {
		return player.getUniqueId();
	}

	@Override
	public int getProperties() {
		return properties;
	}

	@Override
	public void setProperties(int properties) {
		this.properties = properties;
	}
	
}
