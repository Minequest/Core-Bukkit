package com.theminequest.bukkit.platform.entity;

import java.net.InetSocketAddress;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.theminequest.api.Managers;
import com.theminequest.api.platform.MQInventory;
import com.theminequest.api.platform.MQLocation;
import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.bukkit.platform.BukkitInventory;

public class BukkitPlayer implements MQPlayer {
	
	private int properties;
	private String player;
	
	public BukkitPlayer(String player) {
		this.player = player;
		this.properties = 0x00000000;
	}
	
	@Override
	public void chat(String message) {
		Bukkit.getPlayerExact(player).chat(message);
	}
	
	@Override
	public String getCustomName() {
		return Bukkit.getPlayerExact(player).getDisplayName();
	}
	
	@Override
	public String getName() {
		return Bukkit.getPlayerExact(player).getName();
	}
	
	@Override
	public void kick(String message) {
		Bukkit.getPlayerExact(player).kickPlayer(message);
	}
	
	@Override
	public boolean isOnline() {
		return Bukkit.getPlayerExact(player).isOnline();
	}
	
	@Override
	public InetSocketAddress getAddress() {
		return Bukkit.getPlayerExact(player).getAddress();
	}
	
	@Override
	public void sendMessage(String... message) {
		Bukkit.getPlayerExact(player).sendMessage(message);
	}
	
	@Override
	public void setCustomName(String name) {
		Bukkit.getPlayerExact(player).setDisplayName(name);
	}
	
	@Override
	public MQLocation getLocation() {
		return Managers.getPlatform().toLocation(Bukkit.getPlayerExact(player).getLocation());
	}
	
	@Override
	public void teleport(MQLocation location) {
		Location loc = Managers.getPlatform().fromLocation(location);
		Bukkit.getPlayerExact(player).teleport(loc);
	}
	
	@Override
	public MQInventory getInventory() {
		return new BukkitInventory(Bukkit.getPlayerExact(player).getInventory());
	}
	
	@Override
	public double getMaxHealth() {
		return Bukkit.getPlayerExact(player).getMaxHealth();
	}
	
	@Override
	public void setHealth(double health) {
		Bukkit.getPlayerExact(player).setHealth(health);
	}
	
	@Override
	public double getHealth() {
		return Bukkit.getPlayerExact(player).getHealth();
	}

	@Override
	public boolean equals(Object obj) {
		return Bukkit.getPlayerExact(player).getName().equals(((BukkitPlayer)obj).getName());
	}

	@Override
	public int hashCode() {
		return Bukkit.getPlayerExact(player).hashCode();
	}

	@Override
	public void remove() {
		Bukkit.getPlayerExact(player).remove();
	}

	@Override
	public long getEntityId() {
		return Bukkit.getPlayerExact(player).getEntityId();
	}

	@Override
	public UUID getUUID() {
		return Bukkit.getPlayerExact(player).getUniqueId();
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
