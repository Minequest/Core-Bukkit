package com.theminequest.bukkit.platform;

import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;

import com.theminequest.api.Managers;
import com.theminequest.api.platform.MQBlock;
import com.theminequest.api.platform.MQLocation;
import com.theminequest.api.platform.MQMaterial;

public class BukkitBlock implements MQBlock {
	
	private Block block;
	
	public BukkitBlock(Block block) {
		this.block = block;
	}
	
	@Override
	public MQLocation getLocation() {
		return Managers.getPlatform().toLocation(block.getLocation());
	}
	
	@Override
	public Object getOriginalBlock() {
		return block;
	}
	
	@Override
	public MQMaterial getMaterial() {
		return Managers.getPlatform().toMaterial(block.getType());
	}
	
	@Override
	public void setMaterial(MQMaterial material) {
		MaterialData data = material.getUnderlyingObject();
		block.setType(data.getItemType());
		block.setData(data.getData());
	}

	@Override
	public boolean equals(Object obj) {
		BukkitBlock other = (BukkitBlock) obj;
		return block.equals(other.block);
	}
	
}
