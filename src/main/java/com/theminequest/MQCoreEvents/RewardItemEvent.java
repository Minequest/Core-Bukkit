package com.theminequest.MQCoreEvents;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Events.QuestEvent;
import com.theminequest.MineQuest.API.Group.Group;
import com.theminequest.MineQuest.API.Group.QuestGroup;

public class RewardItemEvent extends QuestEvent {
	
	private LinkedHashMap<Integer,Integer> items;

	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
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
		QuestGroup g = Managers.getQuestGroupManager().get(getQuest());
		for (Player p : g.getMembers()){
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
