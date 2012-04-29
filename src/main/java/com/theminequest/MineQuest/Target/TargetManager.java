/**
 * This file, TargetManager.java, is part of MineQuest:
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
 **/
package com.theminequest.MineQuest.Target;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.EventsAPI.QEvent;
import com.theminequest.MineQuest.EventsAPI.TargetedQEvent;
import com.theminequest.MineQuest.Group.Group;
import com.theminequest.MineQuest.Group.Team;
import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Target.TargetDetails.TargetType;
import com.theminequest.MineQuest.Tasks.Task;

public class TargetManager {

	public static List<LivingEntity> getTarget(Quest questid, TargetDetails t) {
		if (t.getType() == TargetType.AREATARGET)
			return areaTarget(questid,t);
		else if (t.getType() == TargetType.AREATARGETQUESTER)
			return areaTargetQuester(questid,t);
		else if (t.getType() == TargetType.TEAMTARGET)
			return partyTarget(questid,t);
		else if (t.getType() == TargetType.TARGETTER)
			return targetter(questid,t);
		else if (t.getType() == TargetType.TARGETTEREDIT)
			return targetteredit(questid,t);
		else if (t.getType() == TargetType.RANDOMTARGET)
			return randomtarget(questid,t);
		return null;
	}

	/*
	 * Details: x:y:z:radius
	 */
	private static List<LivingEntity> areaTarget(Quest q, TargetDetails t) {
		String[] details = t.getDetails().split(":");
		double x = Double.parseDouble(details[0]);
		double y = Double.parseDouble(details[1]);
		double z = Double.parseDouble(details[2]);
		double r = Double.parseDouble(details[3]);
		World w = Bukkit.getWorld(q
				.getWorld());
		Location l = new Location(w, x, y, z);
		return getEntitiesAroundRadius(l, r);
	}

	/*
	 * Quester = PLAYER targetID:radius
	 */
	private static List<LivingEntity> areaTargetQuester(Quest q, TargetDetails t) {
		String[] details = t.getDetails().split(":");
		int targetID = Integer.parseInt(details[0]);
		double r = Double.parseDouble(details[1]);
		List<LivingEntity> es = getTarget(q,q.getTarget(targetID));
		for (LivingEntity en : es) {
			if (en instanceof Player)
				return getEntitiesAroundRadius(en.getLocation(), r);
		}
		return new ArrayList<LivingEntity>();
	}

	/*
	 * this does NOT specify any details (details = "")
	 */
	private static List<LivingEntity> partyTarget(Quest q, TargetDetails t) {
		Group team = MineQuest.groupManager.getGroup(MineQuest.groupManager.indexOfQuest(q));
		List<LivingEntity> list = new ArrayList<LivingEntity>();
		list.addAll(team.getPlayers());
		return list;
	}

	/*
	 * eventid1,eventid2,etc...
	 */
	private static List<LivingEntity> targetter(Quest q, TargetDetails t) {
		String[] ids = t.getDetails().split(",");
		List<LivingEntity> toreturn = new ArrayList<LivingEntity>();
		for (String id : ids) {
			int i = Integer.parseInt(id);
			Task ts = q.getActiveTask();
			Collection<QEvent> running = ts.getEvents();
			for (QEvent event : running) {
				if (event.getEventId() == i) {
					if (event instanceof TargetedQEvent) {
						toreturn.addAll(((TargetedQEvent) event).getTargets());
					}
				}
			}
		}
		return toreturn;
	}
	
	/*
	 * editid1,editid2,editid3...
	 * TODO: actually implement this... even though this doesn't make any sense.
	 */
	@Deprecated
	private static List<LivingEntity> targetteredit(Quest q, TargetDetails t) {
		String[] ids = t.getDetails().split(",");
		List<LivingEntity> toreturn = new ArrayList<LivingEntity>();
		return toreturn;
	}
	
	private static List<LivingEntity> randomtarget(Quest q, TargetDetails t){
		String targetid = t.getDetails();
		List<LivingEntity> toreturn = new ArrayList<LivingEntity>();
		List<LivingEntity> randomchoosing = TargetManager.getTarget(q,q.getTarget(Integer.parseInt(targetid)));
		toreturn.add(randomchoosing.get(new Random().nextInt(randomchoosing.size())));
		return toreturn;
	}

	private static List<LivingEntity> getEntitiesAroundRadius(Location l,
			double radius) {
		List<LivingEntity> toreturn = new ArrayList<LivingEntity>();
		List<LivingEntity> entities = l.getWorld().getLivingEntities();
		for (LivingEntity e : entities) {
			Location el = e.getLocation();
			double distance = el.distance(l);
			if (distance <= radius)
				toreturn.add(e);
		}
		return toreturn;
	}

}
