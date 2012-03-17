package com.theminequest.MineQuest.Editable;

import org.bukkit.event.block.BlockEvent;

public abstract class Edit {
	
	public enum EditType{
		NORMALAREA,INSIDEAREA,OUTSIDEAREA,WITHINHANDTOOLS,BYID;
	}
	
	private EditType type;
	private String details;
	private long questid;
	private int editid;
	
	public Edit(long qid, int eid, EditType t, String d){
		questid = qid;
		editid = eid;
		type = t;
		details = d;
	}
	
	public EditType getType(){
		return type;
	}
	
	public long getQuestId(){
		return questid;
	}
	
	public int getEditId(){
		return editid;
	}
	
	public void onBlockEvent(BlockEvent e){
		
	}

}
