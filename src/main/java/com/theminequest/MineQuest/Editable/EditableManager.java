package com.theminequest.MineQuest.Editable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.theminequest.MineQuest.MineQuest;

public class EditableManager implements Listener{

	private List<Edit> registeredEdits;
	
	public EditableManager(){
		MineQuest.log("[Edit] Starting manager...");
		registeredEdits = new ArrayList<Edit>();
	}
	
	public void registerEdit(Edit e){
		registeredEdits.add(e);
	}
	
	public void deregisterEdit(Edit e){
		registeredEdits.remove(e);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		for (Edit r : registeredEdits){
			if (!e.isCancelled())
				r.onBlockPlace(e);
		}
	}
	
	@EventHandler
	public void onBlockDamage(BlockDamageEvent e){
		for (Edit r : registeredEdits){
			if (!e.isCancelled())
				r.onBlockDamage(e);
		}
	}
	
}
