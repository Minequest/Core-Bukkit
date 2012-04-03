package com.theminequest.MineQuest.Class;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class ClassDetails {
	
	private String name;
	private String displayname;
	private List<String> abilities;
	
	protected ClassDetails(File f) throws IOException{
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		name = f.getName().substring(0, f.getName().indexOf(".cspec"));
		displayname = y.getString("displayname", name);
		abilities = y.getStringList("abilities");
		if (abilities==null)
			throw new IOException("Invalid content!");
	}
	
	public boolean hasAbility(String name){
		return abilities.contains(name);
	}
	
	public String getName(){
		return name;
	}
	
	public String getDisplayName(){
		return displayname;
	}
	
	public List<String> getAbilities(){
		return abilities;
	}

}
