package com.theminequest.MineQuest.Editable;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class AreaEdit extends Edit {

	private int oneX;
	private int oneY;
	private int oneZ;

	private int twoX;
	private int twoY;
	private int twoZ;

	public AreaEdit(long qid, int eid, int tid, String d) {
		super(qid, eid, tid, d);
		String[] s = d.split(":");
		oneX = Integer.parseInt(s[0]);
		oneY = Integer.parseInt(s[1]);
		oneZ = Integer.parseInt(s[2]);
		twoX = Integer.parseInt(s[3]);
		twoY = Integer.parseInt(s[4]);
		twoZ = Integer.parseInt(s[5]);
	}

	public boolean isInArea(Location l){
		int minx = Math.min(oneX, twoX),
				miny = Math.min(oneY, twoY),
				minz = Math.min(oneZ, twoZ),
				maxx = Math.max(oneX, twoX),
				maxy = Math.max(oneY, twoY),
				maxz = Math.max(oneZ, twoZ);
		for(int x = minx; x<=maxx;x++){
			for(int y = miny; y<=maxy;y++){
				for(int z = minz; z<=maxz;z++){
					int locX = l.getBlockX();
					int locY = l.getBlockY();
					int locZ = l.getBlockZ();
					if (locX==x && locY==y && locZ==z)
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean allowEdit(Block b, ItemStack i, Player p) {
		return (isInArea(b.getLocation())==isInside());
	}

	/**
	 * For lack of better use, return true you want to allow
	 * edits inside the area.
	 * @return true if allow edits inside the area
	 */
	public abstract boolean isInside();

}
