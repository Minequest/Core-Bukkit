package com.theminequest.MineQuest;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import com.alta189.simplesave.Database;
import com.alta189.simplesave.DatabaseFactory;
import com.alta189.simplesave.exceptions.ConnectionException;
import com.alta189.simplesave.exceptions.TableRegistrationException;
import com.alta189.simplesave.h2.H2Configuration;
import com.alta189.simplesave.mysql.MySQLConfiguration;
import com.alta189.simplesave.sqlite.SQLiteConfiguration;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Tracker.StatisticManager;
import com.theminequest.MineQuest.API.Utils.PropertiesFile;

public class Statistics implements StatisticManager {
	
	private enum Mode{
		MySQL, SQlite, H2;
	}
	private Mode databasetype;
	private Database backend;
	
	public Statistics() throws ConnectionException{
		Managers.log("[SQL] Loading and connecting to SQL...");
		PropertiesFile config = MineQuest.configuration.databaseConfig;
		String dbtype = config.getString("db_type","h2");
		if (dbtype.equalsIgnoreCase("mysql"))
			databasetype = Mode.MySQL;
		else if (dbtype.equalsIgnoreCase("sqlite"))
			databasetype = Mode.SQlite;
		else
			databasetype = Mode.H2;
		Managers.log("[SQL] Using "+databasetype.name()+" as database.");
		String hostname = config.getString("db_hostname","localhost");
		String port = config.getString("db_port","3306");
		String databasename = config.getString("db_name","minequest");
		String username = config.getString("db_username","root");
		String password = config.getString("db_password","toor");
		if (databasetype == Mode.MySQL)
			backend = DatabaseFactory.createNewDatabase(new MySQLConfiguration().setHost(hostname).setDatabase(databasename).setPort(Integer.parseInt(port)).setUser(username).setPassword(password));
		else if (databasetype == Mode.SQlite) {
			SQLiteConfiguration s = new SQLiteConfiguration();
			s.setPath(Managers.getActivePlugin().getDataFolder().getAbsolutePath() + File.separator + "minequest_sqlite");
			backend = DatabaseFactory.createNewDatabase(s);
		} else
			backend = DatabaseFactory.createNewDatabase(new H2Configuration().setDatabase(Managers.getActivePlugin().getDataFolder().getAbsolutePath() + File.separator + "minequest_h2"));
	}

	@Override
	public Database getStorageBackend() {
		return backend;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Statistic> T getStatistic(String playerName,
			Class<? extends Statistic> tableClazz) {
		Statistic result = backend.select(tableClazz).where().equal("playerName", playerName.toLowerCase()).execute().findOne();
		if (result==null)
			try {
				return (T) tableClazz.getConstructor().newInstance();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return (T) result;
	}

	@Override
	public <T extends Statistic> void setStatistic(T statistic,
			Class<? extends Statistic> tableClazz) {
		backend.save(tableClazz,statistic);
	}

	@Override
	public void registerStatistic(Class<? extends Statistic> tableClazz) {
		try {
			backend.registerTable(tableClazz);
		} catch (TableRegistrationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void connect(boolean connect) throws ConnectionException {
		if (connect)
			backend.connect();
		else
			backend.close();
	}

}
