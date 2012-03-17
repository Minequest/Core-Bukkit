package com.theminequest.MineQuest.Editable;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockEvent;

public abstract class Edit {
	
	private String details;
	private long questid;
	private int editid;
	
	public Edit(long qid, int eid, String d){
		questid = qid;
		editid = eid;
		details = d;
	}
	
	public abstract boolean allowEdit(Block b);
	
	public long getQuestId(){
		return questid;
	}
	
	public int getEditId(){
		return editid;
	}
	
	public void onBlockEvent(BlockEvent e){
		if (((Cancellable)e).isCancelled())
			return;
		if (!allowEdit(e.getBlock())){
			((Cancellable)e).setCancelled(true);
			
		}
	}

}
