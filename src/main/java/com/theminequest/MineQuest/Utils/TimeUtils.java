/*
 * This file, TimeUtils.java, is part of MineQuest:
 * A full featured and customizable quest/mission system.
 * Copyright (C) 2012 The MineQuest Team
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.theminequest.MineQuest.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.World;

import com.theminequest.MineQuest.MineQuest;

public class TimeUtils {
	
	/**
     * A pattern that matches time given in 12-hour form (xx:xx(am|pm))
     */
    protected static final Pattern TWELVE_HOUR_TIME = Pattern.compile("^([0-9]+(?::[0-9]+)?)([apmAPM\\.]+)$");

    /**
     * A Map of time locker tasks for worlds.
     */
    protected static final Map<String, Integer> tasks = new HashMap<String, Integer>();

    /**
     * A Map of world names to time values.
     */
    protected static final Map<String, Integer> lockedTimes = new HashMap<String, Integer>();


	/**
	 * Get locked times.
	 *
	 * @return
	 */
	public static Map<String, Integer> getLockedTimes() {
		return lockedTimes;
	}

	public static synchronized void unlock(World world) {
		Integer id = tasks.get(world.getName());
		if (id != null) {
			MineQuest.activePlugin.getServer().getScheduler().cancelTask(id);
		}
	}

	public static synchronized void lock(World world, long time, long delay) {
		unlock(world);
		int id = MineQuest.activePlugin.getServer().getScheduler().scheduleSyncRepeatingTask(
				MineQuest.activePlugin, new TimeUtils.TimeLocker(world, time), 20, delay);
		tasks.put(world.getName(), id);
	}

	/**
	 * Parse a time string.
	 *
	 * @param timeStr
	 * @return
	 * @throws CommandException
	 */
	public static int matchTime(String timeStr) throws IllegalArgumentException {
		Matcher matcher;

		try {
			int time = Integer.parseInt(timeStr);

			// People tend to enter just a number of the hour
			if (time <= 24) {
				return ((time - 8) % 24) * 1000;
			}

			return time;
		} catch (NumberFormatException e) {
			// Not an integer!
		}

		// Tick time
		if (timeStr.matches("^*[0-9]+$")) {
			return Integer.parseInt(timeStr.substring(1));

			// Allow 24-hour time
		} else if (timeStr.matches("^[0-9]+:[0-9]+$")) {
			String[] parts = timeStr.split(":");
			int hours = Integer.parseInt(parts[0]);
			int mins = Integer.parseInt(parts[1]);
			return (int) (((hours - 8) % 24) * 1000
					+ Math.round((mins % 60) / 60.0 * 1000));

			// Or perhaps 12-hour time
		} else if ((matcher = TWELVE_HOUR_TIME.matcher(timeStr)).matches()) {
			String time = matcher.group(1);
			String period = matcher.group(2);
			int shift;

			if (period.equalsIgnoreCase("am")
					|| period.equalsIgnoreCase("a.m.")) {
				shift = 0;
			} else if (period.equalsIgnoreCase("pm")
					|| period.equalsIgnoreCase("p.m.")) {
				shift = 12;
			} else {
				throw new IllegalArgumentException("'am' or 'pm' expected, got '"
						+ period + "'.");
			}

			String[] parts = time.split(":");
			int hours = Integer.parseInt(parts[0]);
			int mins = parts.length >= 2 ? Integer.parseInt(parts[1]) : 0;
			return (int) ((((hours % 12) + shift - 8) % 24) * 1000
					+ (mins % 60) / 60.0 * 1000);

			// Or some shortcuts
		} else if (timeStr.equalsIgnoreCase("dawn")) {
			return (6 - 8 + 24) * 1000;
		} else if (timeStr.equalsIgnoreCase("sunrise")) {
			return (7 - 8 + 24) * 1000;
		} else if (timeStr.equalsIgnoreCase("morning")) {
			return (24) * 1000;
		} else if (timeStr.equalsIgnoreCase("day")) {
			return (24) * 1000;
		} else if (timeStr.equalsIgnoreCase("midday")
				|| timeStr.equalsIgnoreCase("noon")) {
			return (12 - 8 + 24) * 1000;
		} else if (timeStr.equalsIgnoreCase("afternoon")) {
			return (14 - 8 + 24) * 1000;
		} else if (timeStr.equalsIgnoreCase("evening")) {
			return (16 - 8 + 24) * 1000;
		} else if (timeStr.equalsIgnoreCase("sunset")) {
			return (21 - 8 + 24) * 1000;
		} else if (timeStr.equalsIgnoreCase("dusk")) {
			return (21 - 8 + 24) * 1000 + (int) (30 / 60.0 * 1000);
		} else if (timeStr.equalsIgnoreCase("night")) {
			return (22 - 8 + 24) * 1000;
		} else if (timeStr.equalsIgnoreCase("midnight")) {
			return (0 - 8 + 24) * 1000;
		}

		throw new IllegalArgumentException("Time input format unknown.");
	}
	
	public static class TimeLocker implements Runnable {
	    
	    private final World world;
	    private final long time;

	    public TimeLocker(World world, long time) {
	        super();
	        this.world = world;
	        this.time = time;
	    }

	    public void run() {
	        world.setFullTime(time);
	    }

	}
	
}
