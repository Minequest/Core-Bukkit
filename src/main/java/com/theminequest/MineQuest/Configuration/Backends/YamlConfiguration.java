package com.theminequest.MineQuest.Configuration.Backends;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class YamlConfiguration extends FileConfiguration {

	private org.bukkit.configuration.file.YamlConfiguration ymlfile;
	
	public YamlConfiguration(String s) {
		this(new File(s));
	}
	
	public YamlConfiguration(File f) {
		super(f);
		ymlfile = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(f);
	}

	@Override
	public boolean containsPath(String path) {
		return ymlfile.contains(path);
	}

	@Override
	public boolean containsKey(String path) {
		return (ymlfile.get(path)!=null);
	}

	@Override
	public void setBoolean(boolean b, String path) {
		ymlfile.set(path, b);
	}

	@Override
	public void setCharacter(char c, String path) {
		ymlfile.set(path, c);

	}

	@Override
	public void setInteger(int i, String path) {
		ymlfile.set(path, i);
	}

	@Override
	public void setDouble(double d, String path) {
		ymlfile.set(path, d);
	}

	@Override
	public void setLong(long l, String path) {
		ymlfile.set(path, l);
	}

	@Override
	public void setString(String s, String path) {
		ymlfile.set(path, s);
	}

	@Override
	public void setBooleanList(List<Boolean> b, String path) {
		ymlfile.set(path, b);
	}

	@Override
	public void setCharacterList(List<Character> c, String path) {
		ymlfile.set(path, c);
	}

	@Override
	public void setIntegerList(List<Integer> i, String path) {
		ymlfile.set(path, i);
	}

	@Override
	public void setDoubleList(List<Double> d, String path) {
		ymlfile.set(path, d);
	}

	@Override
	public void setLongList(List<Long> l, String path) {
		ymlfile.set(path, l);
	}

	@Override
	public void setStringList(List<String> s, String path) {
		ymlfile.set(path, s);
	}

	@Override
	public boolean getBoolean(String path) {
		return ymlfile.getBoolean(path);
	}

	@Override
	public char getCharacter(String path) {
		return ymlfile.getString(path).charAt(0);
	}

	@Override
	public int getInteger(String path) {
		return ymlfile.getInt(path);
	}

	@Override
	public double getDouble(String path) {
		return ymlfile.getDouble(path);
	}

	@Override
	public long getLong(String path) {
		return ymlfile.getLong(path);
	}

	@Override
	public String getString(String path) {
		return ymlfile.getString(path);
	}

	@Override
	public List<Boolean> getBooleanList(String path) {
		return ymlfile.getBooleanList(path);
	}

	@Override
	public List<Character> getCharacterList(String path) {
		return ymlfile.getCharacterList(path);
	}

	@Override
	public List<Integer> getIntegerList(String path) {
		return ymlfile.getIntegerList(path);
	}

	@Override
	public List<Double> getDoubleList(String path) {
		return ymlfile.getDoubleList(path);
	}

	@Override
	public List<Long> getLongList(String path) {
		return ymlfile.getLongList(path);
	}

	@Override
	public List<String> getStringList(String path) {
		return ymlfile.getStringList(path);
	}

	@Override
	public void load(File f) {
		ymlfile = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(f);
	}

	@Override
	public void load(String s) {
		load(new File(s));
	}

	@Override
	public void save() throws IOException {
		ymlfile.save(file);
	}

	@Override
	public void save(File f) throws IOException {
		ymlfile.save(f);
	}

	@Override
	public void save(String s) throws IOException {
		ymlfile.save(s);
	}

}
