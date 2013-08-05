package com.theminequest.bukkit.platform;

import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import com.theminequest.api.Managers;
import com.theminequest.api.platform.MQItemStack;
import com.theminequest.api.platform.MQMaterial;

public class BukkitItemStack extends MQItemStack {
	
	private ItemStack stack;
	
	public BukkitItemStack(ItemStack stack) {
		super(Managers.getPlatform().toMaterial(stack.getType()), stack.getAmount(), stack.getData().getData());
		this.stack = stack;
	}
	
	public ItemStack getUnderlyingStack() {
		return stack;
	}

	@Override
	public int getAmount() {
		return stack.getAmount();
	}

	@Override
	public int getData() {
		return stack.getData().getData();
	}

	@Override
	public MQMaterial getMaterial() {
		return Managers.getPlatform().toMaterial(stack.getType());
	}

	@Override
	public boolean isEmpty() {
		return stack.getAmount() == 0;
	}

	@Override
	public void setAmount(int amount) {
		stack.setAmount(amount);
		super.setAmount(amount);
	}

	@Override
	public void setData(int data) {
		stack.getData().setData((byte) data);
		super.setData(data);
	}

	@Override
	public void setMaterial(MQMaterial material) {
		MaterialData data = ((MaterialData) material.getUnderlyingObject());
		stack.setType(data.getItemType());
		super.setMaterial(material);
	}
	
}
