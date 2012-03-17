package com.theminequest.MineQuest.Editable;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemInHandEdit extends Edit {
	
	private String[] materialsID;

	public ItemInHandEdit(long qid, int eid, int tid, String d) {
		super(qid, eid, tid, d);
		materialsID = d.split(":");
	}

	@Override
	public boolean allowEdit(Block b, ItemStack i, Player p) {
		for (String m : materialsID){
			if (Integer.parseInt(m)==i.getTypeId())
				return true;
		}
		return false;
	}

	

}
