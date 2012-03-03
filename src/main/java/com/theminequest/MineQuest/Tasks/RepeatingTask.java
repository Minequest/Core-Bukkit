package com.theminequest.MineQuest.Tasks;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class RepeatingTask extends Event {

	public static ArrayList<RepeatingTask> events = new ArrayList<RepeatingTask>();
	private int tasknumber;

	public RepeatingTask(JavaPlugin plugin, long ticks){
		super();
		events.add(this);
		final int location = events.indexOf(this);
		tasknumber = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable(){
			@Override
			public void run() {
				Bukkit.getServer().getPluginManager().callEvent(RepeatingTask.events.get(location));
			}
		}, 100L, ticks);
	}

	public void cancelEvent(){
		Bukkit.getServer().getScheduler().cancelTask(tasknumber);
	}

}
