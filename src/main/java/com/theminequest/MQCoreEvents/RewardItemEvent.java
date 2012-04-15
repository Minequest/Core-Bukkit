package com.theminequest.MQCoreEvents;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.BukkitEvents.CompleteStatus;
import com.theminequest.MineQuest.EventsAPI.QEvent;
import com.theminequest.MineQuest.Group.Group;

public class RewardItemEvent extends QEvent {
	
	private LinkedHashMap<Integer,Integer> items;

	public RewardItemEvent(long q, int e, String details) {
		super(q, e, details);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.EventsAPI.QEvent#parseDetails(java.lang.String[])
	 * [n] itemid,qty
	 */
	@Override
	public void parseDetails(String[] details) {
		items = new LinkedHashMap<Integer,Integer>();
		for (String s : details){
			String[] d = s.split(",");
			items.put(Integer.parseInt(d[0]),Integer.parseInt(d[1]));
		}
	}

	@Override
	public boolean conditions() {
		return true;
	}

	@Override
	public CompleteStatus action() {
		long gid = MineQuest.groupManager.indexOfQuest(MineQuest.questManager.getQuest(getQuestId()));
		Group g = MineQuest.groupManager.getGroup(gid);
		for (Player p : g.getPlayers()){
			for (int i : items.keySet()){
				p.getInventory().addItem(new ItemStack(i,items.get(i)));
			}
		}
		return CompleteStatus.SUCCESS;
	}

	@Override
	public Integer switchTask() {
		return null;
	}

}
