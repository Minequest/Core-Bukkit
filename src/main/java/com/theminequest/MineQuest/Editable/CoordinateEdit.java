package com.theminequest.MineQuest.Editable;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CoordinateEdit extends Edit {
	
	private int coordX;
	private int coordY;
	private int coordZ;

	public CoordinateEdit(long qid, int eid, int tid, String d) {
		super(qid, eid, tid, d);
		String[] coords = d.split(":");
		coordX = Integer.parseInt(coords[0]);
		coordY = Integer.parseInt(coords[1]);
		coordZ = Integer.parseInt(coords[2]);
	}

	@Override
	public boolean allowEdit(Block b, ItemStack i, Player p) {
		if (b.getX()==coordX && b.getY()==coordY && b.getZ()==coordZ)
			return true;
		return false;
	}

}
