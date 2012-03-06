package com.theminequest.MineQuest.Quest;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class QuestWorldManip {

	private static Random randGen = new Random();
	
	public static World copyWorld(World w) throws IOException{
		String newname = "mqinstance_"+randGen.nextLong();
		FileUtils.copyDirectory(w.getWorldFolder(), new File(newname));
		
		WorldCreator tmp = new WorldCreator("mqinstance_"+randGen.nextLong());
		tmp.copy(w);
		return Bukkit.createWorld(tmp);
	}
	
	public static void removeWorld(World w) throws IOException{
		String removename = w.getName();
		Bukkit.unloadWorld(w, false);
		FileUtils.deleteDirectory(new File(removename));
	}
	
}
