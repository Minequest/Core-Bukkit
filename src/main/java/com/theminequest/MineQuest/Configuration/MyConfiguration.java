package com.theminequest.MineQuest.Configuration;

import java.util.List;

/**
 * MyConfiguration is a wrapper class for
 * MySQL, SQLite, PropertiesFile, and YamlConfiguration.
 * It is intended to handle these as cleanly as possible.
 * <br>
 * <b>Note: This uses dot separation like sk89q's YAML nodes.</b>
 * @author xu_robert <xu_robert@linux.com>
 *
 */
public interface MyConfiguration {
	
	boolean containsPath(String path);
	boolean containsKey(String path);

	void setBoolean(boolean b, String path);
	void setCharacter(char c, String path);
	void setInteger(int i, String path);
	void setDouble(double d, String path);
	void setLong(long l, String path);
	void setString(String s, String path);

	void setBooleanList(List<Boolean> b, String path);
	void setCharacterList(List<Character> c, String path);
	void setIntegerList(List<Integer> i, String path);
	void setDoubleList(List<Double> d, String path);
	void setLongList(List<Long> l, String path);
	void setStringList(List<String> s, String path);
	
	boolean getBoolean(String path);
	char getCharacter(String path);
	int getInteger(String path);
	double getDouble(String path);
	long getLong(String path);
	String getString(String path);
	
	List<Boolean> getBooleanList(String path);
	List<Character> getCharacterList(String path);
	List<Integer> getIntegerList(String path);
	List<Double> getDoubleList(String path);
	List<Long> getLongList(String path);
	List<String> getStringList(String path);
	
	
}
