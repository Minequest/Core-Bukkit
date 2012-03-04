package com.theminequest.MineQuest.Configuration.Backends;

import java.io.File;
import java.io.IOException;

import com.theminequest.MineQuest.Configuration.MyConfiguration;

public abstract class FileConfiguration implements MyConfiguration {
	
	protected File file;
	
	public FileConfiguration(String s){
		this(new File(s));
	}
	
	public FileConfiguration(File f){
		file = f;
	}

	public abstract void load(File f);
	public abstract void load(String s);
	
	public abstract void save() throws IOException;
	public abstract void save(File f) throws IOException;
	public abstract void save(String s) throws IOException;
	
}
