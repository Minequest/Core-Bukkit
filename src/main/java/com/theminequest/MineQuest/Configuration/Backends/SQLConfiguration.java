package com.theminequest.MineQuest.Configuration.Backends;

import java.util.List;

import lib.PatPeter.SQLibrary.DatabaseHandler;

import com.theminequest.MineQuest.Configuration.MyConfiguration;

public abstract class SQLConfiguration implements MyConfiguration {

	private DatabaseHandler database;
	
	public SQLConfiguration(DatabaseHandler db){
		database = db;
	}

	@Override
	public boolean containsPath(String path) {
		// TODO Auto-generated method stub	
		return false;
	}

	@Override
	public boolean containsKey(String path) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBoolean(boolean b, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacter(char c, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInteger(int i, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDouble(double d, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLong(long l, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setString(String s, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBooleanList(List<Boolean> b, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterList(List<Character> c, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIntegerList(List<Integer> i, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDoubleList(List<Double> d, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLongList(List<Long> l, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStringList(List<String> s, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getBoolean(String path) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public char getCharacter(String path) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInteger(String path) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDouble(String path) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLong(String path) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getString(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Boolean> getBooleanList(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Character> getCharacterList(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getIntegerList(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Double> getDoubleList(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> getLongList(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getStringList(String path) {
		// TODO Auto-generated method stub
		return null;
	}

}
