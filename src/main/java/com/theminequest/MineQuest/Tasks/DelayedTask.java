package com.theminequest.MineQuest.Tasks;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class DelayedTask extends Event {

	public static ArrayList<DelayedTask> events = new ArrayList<DelayedTask>();

	public DelayedTask(JavaPlugin plugin, long delayinticks){
		super();
		events.add(this);
		final int location = events.indexOf(this);
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable(){
			@Override
			public void run() {
				Bukkit.getServer().getPluginManager().callEvent(RepeatingTask.events.get(location));
				// not removing. don't want to resize down. null out instead.
				events.set(location, null);
			}
		}, delayinticks);
	}

	
}
