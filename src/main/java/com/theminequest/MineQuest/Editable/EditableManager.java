/**
 * This file, EditableManager.java, is part of MineQuest:
 * A full featured and customizable quest/mission system.
 * Copyright (C) 2012 The MineQuest Team
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 **/
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
	
	public synchronized void registerEdit(Edit e){
		registeredEdits.add(e);
	}
	
	public synchronized void deregisterEdit(Edit e){
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
