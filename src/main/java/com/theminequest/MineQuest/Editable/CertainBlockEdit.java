package com.theminequest.MineQuest.Editable;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CertainBlockEdit extends Edit {

	private String[] blockIDs;
	
	public CertainBlockEdit(long qid, int eid, int tid, String d) {
		super(qid, eid, tid, d);
		blockIDs = d.split(":");
	}

	@Override
	public boolean allowEdit(Block b, ItemStack i, Player p) {
		for (String s : blockIDs){
			if (Integer.parseInt(s)==b.getTypeId())
				return true;
		}
		return false;
	}

}
