/*
 * This file is part of MineQuest, The ultimate MMORPG plugin!.
 * MineQuest is licensed under GNU General Public License v3.
 * Copyright (C) 2012 The MineQuest Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.theminequest.bukkit.group;

import static com.theminequest.common.util.I18NMessage._;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.theminequest.api.ManagerException;
import com.theminequest.api.ManagerException.ManagerReason;
import com.theminequest.api.Managers;
import com.theminequest.api.group.Group;
import com.theminequest.api.group.GroupException;
import com.theminequest.api.group.GroupManager;
import com.theminequest.api.platform.MQPlayer;
import com.theminequest.api.platform.event.GroupInviteEvent;
import com.theminequest.api.quest.Quest;

public class BukkitGroupManager implements Listener, GroupManager {
	
	public final int TEAM_MAX_CAPACITY;
	public final int SUPER_MAX_CAPACITY;
	private Map<Long, Group> groups;
	private Map<MQPlayer, Group> invitations;
	private long groupid;
	
	public BukkitGroupManager() {
		Managers.log("[Party] Starting Manager...");
		groups = Collections.synchronizedMap(new LinkedHashMap<Long, Group>());
		invitations = Collections.synchronizedMap(new LinkedHashMap<MQPlayer, Group>());
		groupid = 0;
		TEAM_MAX_CAPACITY = Managers.getPlatform().getConfigurationFile().getInt("group.team.maxCapacity", 8);
		SUPER_MAX_CAPACITY = Managers.getPlatform().getConfigurationFile().getInt("group.super.maxCapacity", 3);
	}
	
	@Override
	public synchronized Group createNewGroup(List<MQPlayer> p) {
		long id = groupid;
		groupid++;
		Party party = new Party(groupid, p, TEAM_MAX_CAPACITY);
		groups.put(id, party);
		return party;
	}
	
	@Override
	public synchronized Group createNewGroup(MQPlayer p) {
		List<MQPlayer> group = new ArrayList<MQPlayer>();
		group.add(p);
		return createNewGroup(group);
	}
	
	@Override
	public synchronized Group get(long id) {
		return groups.get(id);
	}
	
	@Override
	public synchronized Group get(Quest activeQuest) {
		if (!activeQuest.isInstanced())
			// create faux Group with fake methods
			// and return that for events and such to use
			// get player from getQuestOwner()
			return new SingleParty(Managers.getPlatform().getPlayer(activeQuest.getQuestOwner()), activeQuest);
		return get(indexOf(activeQuest));
	}
	
	/**
	 * Determine if a player is on a team.
	 * 
	 * @param p
	 *            Player to check for.
	 * @return Party ID, or -1 if not on team.
	 */
	@Override
	public synchronized long indexOf(MQPlayer p) {
		for (long id : groups.keySet()) {
			Group t = groups.get(id);
			if ((t != null) && t.contains(p))
				return id;
		}
		return -1;
	}
	
	/**
	 * Determine if a quest is being played by a team
	 * 
	 * @param q
	 *            Quest
	 * @return Party ID, or -1 if not on a team.
	 */
	@Override
	public synchronized long indexOf(Quest q) {
		for (long id : groups.keySet()) {
			Group t = groups.get(id);
			if ((t != null) && (t.getQuest() != null) && t.getQuest().equals(q))
				return id;
		}
		return -1;
	}
	
	@Override
	public synchronized void acceptInvite(MQPlayer p) throws ManagerException {
		if (!invitations.containsKey(p))
			throw new ManagerException(ManagerReason.INVALIDARGS);
		try {
			invitations.get(p).add(p);
		} catch (GroupException e) {
			throw new ManagerException(e);
		}
		invitations.remove(p);
	}
	
	@Override
	public synchronized boolean hasInvite(MQPlayer p) {
		return (invitations.containsKey(p));
	}
	
	@Override
	public synchronized void invite(final MQPlayer p, Group g) throws ManagerException {
		if (invitations.containsKey(p))
			throw new ManagerException(ManagerReason.INVALIDARGS);
		
		invitations.put(p, g);
		
		// TODO Call GroupInviteEvent (remember, 30 seconds to accept invite)
		GroupInviteEvent event = new GroupInviteEvent(g.getLeader().getName(), p, g.getID());
		Managers.getPlatform().callEvent(event);
		
		p.sendMessage(ChatColor.GOLD + "[" + _("Group") + "] " + ChatColor.RESET + _("You've been invited to a group by {0}.", g.getLeader().getName()));
		p.sendMessage(_("Accept the group invite (within 30 seconds) by using {0}.", "/party accept"));
		
		Managers.getPlatform().scheduleAsynchronousTask(new Runnable() {
			
			@Override
			public void run() {
				denyInvite(p);
			}
			
		}, 600);
	}
	
	@Override
	public synchronized void denyInvite(MQPlayer p) {
		if (!invitations.containsKey(p)) // accepted; just return.
			return;
		invitations.remove(p);
		
		p.sendMessage(_("Invite denied!")); // FIXME
	}
	
	/*
	 * Only called by Party objects when everyone leaves the team.
	 */
	protected synchronized void removeEmptyGroup(long id) {
		if (groups.get(id) == null)
			return;
		if (groups.get(id).getMembers().size() > 0)
			throw new IllegalArgumentException("Party is still full!");
		try {
			groups.get(id).setCapacity(0);
		} catch (GroupException e) {
			e.printStackTrace();
		}
		groups.remove(id);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public synchronized void onPlayerQuit(PlayerQuitEvent e) {
		processEvent(e);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public synchronized void onPlayerKick(PlayerKickEvent e) {
		processEvent(e);
	}
	
	private synchronized void processEvent(PlayerEvent event) {
		
		MQPlayer player = Managers.getPlatform().toPlayer(event.getPlayer());
		
		long team = indexOf(player);
		if (team != -1) {
			try {
				groups.get(team).remove(player);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public synchronized void groupQuestFinish(Group g, Quest quest) {
		if (!quest.isInstanced()) {
			switch (quest.isFinished()) {
			case CANCELED:
			case IGNORE:
				break;
			default:
				try {
					g.finishQuest();
				} catch (GroupException e) {
					throw new RuntimeException(e);
				}
			}
		}
		
	}
	
	@Override
	public void disposeGroup(Group group) {
		for (MQPlayer p : group.getMembers()) {
			p.sendMessage(_("Group Disposal: If you're getting this message, notify the administrator immediately."));
			try {
				group.remove(p);
			} catch (GroupException e) {
				e.printStackTrace();
			}
		}
		Iterator<Group> i1 = groups.values().iterator();
		while (i1.hasNext()) {
			Group g = i1.next();
			if (g.equals(group)) {
				i1.remove();
				break;
			}
		}
		Iterator<Group> i2 = invitations.values().iterator();
		while (i2.hasNext()) {
			Group g = i2.next();
			if (g.equals(group)) {
				i2.remove();
				break;
			}
		}
		return;
	}
	
	@Override
	public Group get(MQPlayer p) {
		return get(indexOf(p));
	}
	
	@Override
	public void groupPlayerJoin(Group g, MQPlayer player) {
		for (MQPlayer p : g.getMembers())
			p.sendMessage(ChatColor.GOLD + _("{0} joined the group.", player.getDisplayName()));
	}
	
	@Override
	public void groupPlayerQuit(Group g, MQPlayer player) {
		for (MQPlayer p : g.getMembers())
			p.sendMessage(ChatColor.GOLD + _("{0} left the group.", player.getDisplayName()));
	}
}
