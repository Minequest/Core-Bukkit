package com.theminequest.bukkit.frontend.inventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.theminequest.bukkit.frontend.inventory.IconMenu.OptionClickEvent;
import com.theminequest.bukkit.frontend.inventory.IconMenu.OptionClickEventHandler;

/**
 * 
 * @author nisovin, from Bukkit forums.
 *         (public domain)
 *         https://forums.bukkit.org/threads/icon-menu.108342/
 * 
 */
public class TemporaryIconMenu implements Listener {
	
	private String name;
	private int size;
	private Map<Integer, Action> actions;
	private OptionClickEventHandler handler;
	private Plugin plugin;
	
	private String[] optionNames;
	private ItemStack[] optionIcons;
	
	public TemporaryIconMenu(String name, int size, Plugin plugin) {
		this.name = name;
		this.size = size;
		this.actions = new HashMap<Integer, Action>();
		this.handler = new OptionClickEventHandler() {
			
			@Override
			public void onOptionClick(OptionClickEvent event) {
				Action act = actions.get(event.getPosition());
				if (act != null) {
					act.run(event);
					event.setWillClose(true);
					event.setWillDestroy(true);
				}
			}
			
		};
		this.plugin = plugin;
		this.optionNames = new String[size];
		this.optionIcons = new ItemStack[size];
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public TemporaryIconMenu setOption(int position, ItemStack icon, Action action, String name, String... info) {
		optionNames[position] = name;
		optionIcons[position] = setItemNameAndLore(icon, name, info);
		actions.put(position, action);
		return this;
	}
	
	public void open(Player player) {
		Inventory inventory = Bukkit.createInventory(player, size, name);
		for (int i = 0; i < optionIcons.length; i++) {
			if (optionIcons[i] != null) {
				inventory.setItem(i, optionIcons[i]);
			}
		}
		player.openInventory(inventory);
	}
	
	public void destroy() {
		HandlerList.unregisterAll(this);
		handler = null;
		plugin = null;
		optionNames = null;
		optionIcons = null;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	void onInventoryClose(InventoryCloseEvent event) {
		if (event.getInventory().getTitle().equals(name)) {
			destroy();
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle().equals(name)) {
			event.setCancelled(true);
			int slot = event.getRawSlot();
			if (slot >= 0 && slot < size && optionNames[slot] != null) {
				Plugin plugin = this.plugin;
				OptionClickEvent e = new OptionClickEvent((Player) event.getWhoClicked(), slot, optionNames[slot]);
				handler.onOptionClick(e);
				if (e.willClose()) {
					final Player p = (Player) event.getWhoClicked();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							p.closeInventory();
						}
					}, 1);
				}
				if (e.willDestroy()) {
					destroy();
				}
			}
		}
	}
	
	private ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
	
	public interface Action {
		void run(OptionClickEvent event);
	}
	
}
