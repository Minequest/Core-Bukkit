package com.theminequest.bukkit.util;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {
	public static boolean inventoryContains(Inventory inv, Material material, int data, int amount) {
		Collection<? extends ItemStack> stacks = inv.all(material).values();
		if (stacks.size() == 0)
			return false;
		int count = 0;
		for (ItemStack stack : stacks) {
			if (material.equals(Material.POTION)) {
				if (stack.getDurability() == data) {
					count += stack.getAmount();
				}
			} else if (stack.getData().getData() == data) {
				count += stack.getAmount();
			}
		}
		return count >= amount;
	}
}
