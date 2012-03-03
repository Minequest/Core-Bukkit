package com.theminequest.MineQuest.Tasks;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class DelayedTask extends Task {

	public static ArrayList<DelayedTask> events = new ArrayList<DelayedTask>();

	public DelayedTask(JavaPlugin plugin, long delayinticks, long questid, int taskid){
		super(questid, taskid);
		events.add(this);
		final int location = events.indexOf(this);
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable(){
			@Override
			public void run() {
				RepeatingTask.events.get(location).start();
				// not removing. don't want to resize down. null out instead.
				events.set(location, null);
			}
		}, delayinticks);
	}

	
}
