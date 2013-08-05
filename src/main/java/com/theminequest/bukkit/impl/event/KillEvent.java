package com.theminequest.bukkit.impl.event;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.theminequest.api.CompleteStatus;
import com.theminequest.api.Managers;
import com.theminequest.api.group.Group;
import com.theminequest.api.platform.MQPlayer;
import com.theminequest.api.quest.event.QuestEvent;
import com.theminequest.api.quest.event.UserQuestEvent;
import com.theminequest.bukkit.util.MobUtils;

public class KillEvent extends QuestEvent implements UserQuestEvent, Listener {
	
	private Map<EntityType, Integer> killMap;
	private Map<EntityType, Integer> currentKills;
	private int taskid;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * [0]: task id to trigger
	 * [1]: entities
	 * [2]: total # to kill
	 */
	@Override
	public void setupArguments(String[] details) {
		taskid = Integer.parseInt(details[0]);
		killMap = Collections.synchronizedMap(new LinkedHashMap<EntityType, Integer>());
		currentKills = Collections.synchronizedMap(new HashMap<EntityType, Integer>());
		String[] entities = details[1].split(",");
		String[] amounts = details[2].split(",");
		for (int i = 0; i < entities.length; i++) {
			String entity = entities[i];
			Integer amount = null;
			try {
				if (amounts.length == 1)
					amount = Integer.valueOf(amounts[0]);
				else if (i < amounts.length)
					amount = Integer.valueOf(amounts[i]);
			} catch (NumberFormatException e) {
			}
			
			if (amount == null) {
				Managers.log(Level.SEVERE, "[Event] In KillEvent, could not determine number of kills for " + entity);
				continue;
			}
			
			EntityType m = MobUtils.getEntityType(entity);
			
			if (m == null) {
				Managers.log(Level.SEVERE, "[Event] In KillEvent, could not determine mob type for " + entity);
				continue;
			}
			killMap.put(m, amount);
		}
	}
	
	@Override
	public boolean conditions() {
		synchronized (killMap) {
			for (Map.Entry<EntityType, Integer> entry : killMap.entrySet()) {
				Integer kills = currentKills.get(entry.getKey());
				if ((kills == null) || (kills.intValue() < entry.getValue().intValue()))
					return false;
			}
		}
		return true;
	}
	
	@Override
	public CompleteStatus action() {
		return CompleteStatus.SUCCESS;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theminequest.MineQuest.Events.QEvent#entityDeathCondition(org.bukkit
	 * .event.entity.EntityDeathEvent)
	 */
	@EventHandler
	public boolean entityDeathCondition(EntityDeathEvent e) {
		if (!(e.getEntity() instanceof LivingEntity))
			return false;
		LivingEntity el = e.getEntity();
		if (!(el.getLastDamageCause() instanceof EntityDamageByEntityEvent))
			return false;
		
		EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent) el.getLastDamageCause();
		Player p = null;
		if (edbee.getDamager() instanceof Player)
			p = (Player) edbee.getDamager();
		else if (edbee.getDamager() instanceof Projectile) {
			Projectile projectile = (Projectile) edbee.getDamager();
			if (projectile.getShooter() instanceof Player)
				p = (Player) projectile.getShooter();
		} else if (edbee.getDamager() instanceof Tameable) {
			Tameable tameable = (Tameable) edbee.getDamager();
			if (tameable.getOwner() instanceof Player)
				p = (Player) tameable.getOwner();
		}
		
		if (p == null)
			return false;
		
		Group g = Managers.getGroupManager().get(getQuest());
		List<MQPlayer> team = g.getMembers();
		if (team.contains(p))
			if (currentKills.containsKey(el.getType())) {
				int count = currentKills.get(el.getType());
				currentKills.put(el.getType(), count + 1);
			} else
				currentKills.put(el.getType(), 1);
		return false;
	}
	
	@Override
	public void setUpEvent() {
		Bukkit.getPluginManager().registerEvents(this, (JavaPlugin) Managers.getPlatform().getPlatformObject());
	}

	@Override
	public void cleanUpEvent() {
		HandlerList.unregisterAll(this);
	}

	@Override
	public Integer switchTask() {
		return taskid;
	}
	
	@Override
	public String getDescription() {
		StringBuilder builder = new StringBuilder();
		builder.append("Kill ");
		boolean first = true;
		int i = 0;
		synchronized (killMap) {
			for (Map.Entry<EntityType, Integer> entry : killMap.entrySet()) {
				i++;
				if (first)
					first = false;
				else {
					builder.append(", ");
					
					if (i == killMap.size())
						builder.append("and ");
				}
				
				builder.append(entry.getValue().toString()).append(" ").append(entry.getKey().getName());
			}
		}
		builder.append("!");
		return builder.toString();
	}
	
}
