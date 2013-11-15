package com.theminequest.bukkit.impl.event;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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
		ident = "RewardNameLoreEvent",
		description = "Reward players an item with a name and some lore.",
		arguments = { "Next Task", "Item", "Durability", "Name", "Lore" },
		typeArguments = { DocArgType.INT, DocArgType.STRING, DocArgType.FLOAT, DocArgType.STRING, DocArgType.STRARRAY }
		)
public class RewardNameLoreEvent extends QuestEvent {
	
	private int taskid;
	private Material item;
	private short itemDurability;
	private String name;
	private List<String> lore;
	
	@Override
	public void setupArguments(String[] arguments) {
		taskid = Integer.parseInt(arguments[0]);
		item = ItemUtils.getMaterial(arguments[1]);
		itemDurability = Short.parseShort(arguments[2]);
		name = ChatUtils.colorize(arguments[3]);
		lore = new LinkedList<String>();
		for (int i = 4; i < arguments.length; i++)
			lore.add(ChatUtils.colorize(arguments[i]));
	}
	
	@Override
	public boolean conditions() {
		return true;
	}
	
	@Override
	public CompleteStatus action() {
		Group g = Managers.getGroupManager().get(getQuest());
		for (MQPlayer p : g.getMembers()) {
			
			Player bPlayer = Bukkit.getPlayerExact(p.getName());
			
			ItemStack itemStack = new ItemStack(item);
			itemStack.setDurability(itemDurability);
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
