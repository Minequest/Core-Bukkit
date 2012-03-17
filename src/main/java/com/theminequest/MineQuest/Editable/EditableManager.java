package com.theminequest.MineQuest.Editable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockEvent;

public class EditableManager implements Listener{

	private List<Edit> registeredEdits;
	
	public EditableManager(){
		registeredEdits = new ArrayList<Edit>();
	}
	
	public void registerEdit(Edit e){
		
	}
	
	public void deregisterEdit(Edit e){
		
	}
	
	@EventHandler
	public void onBlockChange(BlockEvent e){
		try {
			((Cancellable)e).isCancelled();
		}catch (ClassCastException ex){
			return;
		}
		for (Edit r : registeredEdits)
			r.onBlockEvent(e);
	}
	
}
