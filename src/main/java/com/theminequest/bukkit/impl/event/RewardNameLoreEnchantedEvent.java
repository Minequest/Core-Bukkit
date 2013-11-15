package com.theminequest.bukkit.impl.event;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.theminequest.api.CompleteStatus;
import com.theminequest.api.Managers;
import com.theminequest.api.group.Group;
import com.theminequest.api.platform.entity.MQPlayer;
import com.theminequest.api.quest.event.QuestEvent;
import com.theminequest.api.util.ChatUtils;
import com.theminequest.bukkit.util.ItemUtils;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Event",
		ident = "RewardNameLoreEnchantedEvent",
		description = "Reward players an enchanted item with a name and some lore.",
		arguments = { "Next Task", "Item", "Durability", "Enchanted Type #", "Enchanted Level", "Name", "Lore" },
		typeArguments = { DocArgType.INT, DocArgType.STRING, DocArgType.FLOAT, DocArgType.INT, DocArgType.INT, DocArgType.STRING, DocArgType.STRARRAY }
		)
public class RewardNameLoreEnchantedEvent extends QuestEvent {
	
	private int taskid;
	private Material item;
	private short itemDurability;
	private int enchantNumber;
	private int enchantLevel;
	private String name;
	private List<String> lore;
	
	@Override
	public void setupArguments(String[] arguments) {
		taskid = Integer.parseInt(arguments[0]);
		item = ItemUtils.getMaterial(arguments[1]);
		itemDurability = Short.parseShort(arguments[2]);
		enchantNumber = Integer.parseInt(arguments[3]);
		enchantLevel = Integer.parseInt(arguments[4]);
		name = ChatUtils.colorize(arguments[5]);
		lore = new LinkedList<String>();
		for (int i = 6; i < arguments.length; i++)
			lore.add(ChatUtils.colorize(arguments[i]));
	}
	
	@Override
	public boolean conditions() {
		return true;
	}
	
	@Override
	public CompleteStatus action() {
		Enchantment enchantment = Enchantment.getById(enchantNumber);
		if (enchantment == null)
			return CompleteStatus.FAIL;

		Group g = Managers.getGroupManager().get(getQuest());
		for (MQPlayer p : g.getMembers()) {
			
			Player bPlayer = Bukkit.getPlayerExact(p.getName());
			
			ItemStack itemStack = new ItemStack(item);
			itemStack.setDurability(itemDurability);
			itemStack.addUnsafeEnchantment(enchantment, enchantLevel);
			itemStack.getItemMeta().setDisplayName(name);
			itemStack.getItemMeta().setLore(lore);
			
			bPlayer.getInventory().addItem(itemStack);
		}
		return CompleteStatus.SUCCESS;
	}
	
	@Override
	public Integer switchTask() {
		return taskid;
	}
	
}
