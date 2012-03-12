package com.theminequest.MineQuest.Target;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.EventsAPI.QEvent;
import com.theminequest.MineQuest.EventsAPI.TargetEvent;
import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Target.TargetDetails.TargetType;
import com.theminequest.MineQuest.Tasks.Task;
import com.theminequest.MineQuest.Team.Team;

public class TargetManager {

	public static List<LivingEntity> getTarget(TargetDetails t) {
		if (t.getType() == TargetType.AREATARGET)
			return areaTarget(t);
		else if (t.getType() == TargetType.AREATARGETQUESTER)
			return areaTargetQuester(t);
		else if (t.getType() == TargetType.TEAMTARGET)
			return partyTarget(t);
		return null;
	}

	/*
	 * Details: x:y:z:radius
	 */
	private static List<LivingEntity> areaTarget(TargetDetails t) {
		String[] details = t.getDetails().split(":");
		double x = Double.parseDouble(details[0]);
		double y = Double.parseDouble(details[1]);
		double z = Double.parseDouble(details[2]);
		double r = Double.parseDouble(details[3]);
		World w = Bukkit.getWorld(MineQuest.questManager.getQuest(t.getQuest())
				.getWorld());
		Location l = new Location(w, x, y, z);
		return getEntitiesAroundRadius(l, r);
	}

	/*
	 * Quester = PLAYER targetID:radius
	 */
	private static List<LivingEntity> areaTargetQuester(TargetDetails t) {
		String[] details = t.getDetails().split(":");
		int targetID = Integer.parseInt(details[0]);
		double r = Double.parseDouble(details[1]);
		List<LivingEntity> es = getTarget(MineQuest.questManager.getQuest(
				t.getQuest()).getTarget(targetID));
		for (LivingEntity en : es) {
			if (en instanceof Player)
				return getEntitiesAroundRadius(en.getLocation(), r);
		}
		return new ArrayList<LivingEntity>();
	}

	/*
	 * this does NOT specify any details (details = "")
	 */
	private static List<LivingEntity> partyTarget(TargetDetails t) {
		Quest q = MineQuest.questManager.getQuest(t.getQuest());
		Team team = q.getTeam();
		List<LivingEntity> list = new ArrayList<LivingEntity>();
		list.addAll(team.getPlayers());
		return list;
	}

	/*
	 * eventid1:eventid2:etc...
	 */
	private static List<LivingEntity> targetter(TargetDetails t) {
		String[] ids = t.getDetails().split(":");
		List<LivingEntity> toreturn = new ArrayList<LivingEntity>();
		for (String id : ids) {
			int i = Integer.parseInt(id);
			Quest q = MineQuest.questManager.getQuest(t.getQuest());
			Task ts = q.getTask(q.getCurrentTaskID());
			List<QEvent> running = ts.getEventsRunning();
			for (QEvent event : running) {
				if (event.getEventId() == i) {
					if (event instanceof TargetEvent) {
						toreturn.addAll(((TargetEvent) event).getTargets());
					}
				}
			}
		}
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
