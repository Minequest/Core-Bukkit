package com.theminequest.bukkit.platform;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import com.theminequest.api.platform.MQMaterial;

public class BukkitMaterial implements MQMaterial {
	
	private Material material;
	private int data;
	
	public BukkitMaterial(Material material) {
		this.material = material;
		this.data = 0;
	}
	
	public BukkitMaterial(MaterialData data) {
		this.material = data.getItemType();
		this.data = data.getData();
	}
	
	@Override
	public int getData() {
		return data;
	}
	
	@Override
	public String getDisplayName() {
		return material.toString();
	}
	
	@Override
	public int getMaxStackSize() {
		return material.getMaxStackSize();
	}
	
	@Override
	public String getName() {
		return material.name();
	}
	
	@Override
	public void setDisplayName(String displayName) {}
	
	@Override
	public void setMaxStackSize(int newValue) {}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E> E getUnderlyingObject() {
		return (E) material.getNewData((byte) data);
	}
	
}
