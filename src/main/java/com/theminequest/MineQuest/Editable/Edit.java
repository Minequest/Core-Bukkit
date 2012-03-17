package com.theminequest.MineQuest.Editable;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.getspout.commons.ChatColor;

import com.theminequest.MineQuest.MineQuest;

public abstract class Edit {
	
	private String details;
	private long questid;
	private int editid;
	private int taskid;
	
	public Edit(long qid, int eid, int tid, String d){
		questid = qid;
		editid = eid;
		taskid = tid;
		details = d;
	}
	
	/**
	 * Check to see if editing is allowed with the given block
	 * @param b Block that is being edited
	 * @return true if allowed
	 */
	public abstract boolean allowEdit(Block b, ItemStack i, Player p);
	
	public long getQuestId(){
		return questid;
	}
	
	public int getEditId(){
		return editid;
	}
	
	public void onBlockPlace(BlockPlaceEvent e){
		if (!allowEdit(e.getBlock(), e.getItemInHand(), e.getPlayer())){
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.YELLOW+"[!] " +MineQuest.questManager.getQuest(questid).getEditMessage());
		}
		MineQuest.questManager.getQuest(questid).switchTaskTo(taskid);
	}
	
	public void onBlockDamage(BlockDamageEvent e){
		if (!allowEdit(e.getBlock(), e.getItemInHand(), e.getPlayer())){
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.YELLOW+"[!] " +MineQuest.questManager.getQuest(questid).getEditMessage());
		}
		MineQuest.questManager.getQuest(questid).switchTaskTo(taskid);
	}

}
