package com.theminequest.MineQuest.Configuration.Backends;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.theminequest.MineQuest.Configuration.PropertiesFile;

public class PropertiesConfiguration extends FileConfiguration {

	private PropertiesFile properties;
	
	public PropertiesConfiguration(File f) {
		super(f);
		properties = new PropertiesFile(f.getAbsolutePath());
	}
	
	public PropertiesConfiguration(String s) {
		this(new File(s));
	}

	@Override
	public boolean containsPath(String path) {
		return properties.containsKey(path);
	}

	@Override
	public boolean containsKey(String path) {
		return properties.containsKey(path);
	}

	@Override
	public void setBoolean(boolean b, String path) {
		properties.setBoolean(path, b);
	}

	@Override
	public void setCharacter(char c, String path) {
		properties.setString(path,String.valueOf(c));
	}

	@Override
	public void setInteger(int i, String path) {
		properties.setInt(path, i);

	}

	@Override
	public void setDouble(double d, String path) {
		properties.setDouble(path, d);
	}

	@Override
	public void setLong(long l, String path) {
		properties.setLong(path, l);
	}

	@Override
	public void setString(String s, String path) {
		properties.setString(path, s);
	}

	@Override
	public void setBooleanList(List<Boolean> b, String path) {
		int former = properties.getInt(path, -1);
		if (former != -1){
			for (int i=0; i<former; i++){
				properties.removeKey(path+":"+i);
			}
		}
		properties.setInt(path, b.size());
		for (int i=0; i<b.size(); i++){
			properties.setBoolean(path+":"+i, b.get(i));
		}
	}

	@Override
	public void setCharacterList(List<Character> c, String path) {
		int former = properties.getInt(path, -1);
		if (former != -1){
			for (int i=0; i<former; i++){
				properties.removeKey(path+":"+i);
			}
		}
		properties.setInt(path, c.size());
		for (int i=0; i<c.size(); i++){
			properties.setString(path+":"+i, String.valueOf(c.get(i)));
		}
	}

	@Override
	public void setIntegerList(List<Integer> il, String path) {
		int former = properties.getInt(path, -1);
		if (former != -1){
			for (int i=0; i<former; i++){
				properties.removeKey(path+":"+i);
			}
		}
		properties.setInt(path, il.size());
		for (int i=0; i<il.size(); i++){
			properties.setInt(path+":"+i, il.get(i));
		}
	}

	@Override
	public void setDoubleList(List<Double> d, String path) {
		int former = properties.getInt(path, -1);
		if (former != -1){
			for (int i=0; i<former; i++){
				properties.removeKey(path+":"+i);
			}
		}
		properties.setInt(path, d.size());
		for (int i=0; i<d.size(); i++){
			properties.setDouble(path+":"+i, d.get(i));
		}
	}

	@Override
	public void setLongList(List<Long> l, String path) {
		int former = properties.getInt(path, -1);
		if (former != -1){
			for (int i=0; i<former; i++){
				properties.removeKey(path+":"+i);
			}
		}
		properties.setInt(path, l.size());
		for (int i=0; i<l.size(); i++){
			properties.setLong(path+":"+i, l.get(i));
		}
	}

	@Override
	public void setStringList(List<String> s, String path) {
		int former = properties.getInt(path, -1);
		if (former != -1){
			for (int i=0; i<former; i++){
				properties.removeKey(path+":"+i);
			}
		}
		properties.setInt(path, s.size());
		for (int i=0; i<s.size(); i++){
			properties.setString(path+":"+i, s.get(i));
		}
	}

	@Override
	public boolean getBoolean(String path) {
		return properties.getBoolean(path);
	}
	
	@Override
	public char getCharacter(String path){
		return properties.getString(path).charAt(0);
	}

	@Override
	public int getInteger(String path) {
		return properties.getInt(path);
	}

	@Override
	public double getDouble(String path) {
		return properties.getDouble(path);
	}

	@Override
	public long getLong(String path) {
		return properties.getLong(path);
	}

	@Override
	public String getString(String path) {
		return properties.getString(path);
	}

	@Override
	public List<Boolean> getBooleanList(String path) {
		int size = properties.getInt(path);
		List<Boolean> tr = new ArrayList<Boolean>();
		for (int i=0; i<size; i++){
			tr.add(properties.getBoolean(path+":"+i));
		}
		return tr;
	}

	@Override
	public List<Character> getCharacterList(String path) {
		int size = properties.getInt(path);
		List<Character> tr = new ArrayList<Character>();
		for (int i=0; i<size; i++){
			tr.add(properties.getString(path+":"+i).charAt(0));
		}
		return tr;
	}

	@Override
	public List<Integer> getIntegerList(String path) {
		int size = properties.getInt(path);
		List<Integer> tr = new ArrayList<Integer>();
		for (int i=0; i<size; i++){
			tr.add(properties.getInt(path+":"+i));
		}
		return tr;
	}

	@Override
	public List<Double> getDoubleList(String path) {
		int size = properties.getInt(path);
		List<Double> tr = new ArrayList<Double>();
		for (int i=0; i<size; i++){
			tr.add(properties.getDouble(path+":"+i));
		}
		return tr;
	}

	@Override
	public List<Long> getLongList(String path) {
		int size = properties.getInt(path);
		List<Long> tr = new ArrayList<Long>();
		for (int i=0; i<size; i++){
			tr.add(properties.getLong(path+":"+i));
		}
		return tr;
	}

	@Override
	public List<String> getStringList(String path) {
		int size = properties.getInt(path);
		List<String> tr = new ArrayList<String>();
		for (int i=0; i<size; i++){
			tr.add(properties.getString(path+":"+i));
		}
		return tr;
	}

	@Override
	public void load(File f) {
		properties = new PropertiesFile(f.getAbsolutePath());
	}

	@Override
	public void load(String s) {
		properties = new PropertiesFile(s);
	}

	@Override
	public void save() throws IOException {
		properties.save();
		properties.load();
	}

	@Override
	public void save(File f) throws IOException {
		properties.save(f);
	}

	@Override
	public void save(String s) throws IOException {
		properties.save(new File(s));
	}

}
