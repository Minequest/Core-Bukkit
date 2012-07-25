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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.theminequest.MineQuest.Quest;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Group.QuestGroup;
import com.theminequest.MineQuest.API.Quest.QuestRequirement;
import com.theminequest.MineQuest.API.Tracker.LogStatus;
import com.theminequest.MineQuest.API.Tracker.QuestStatisticUtils;
import com.theminequest.MineQuest.API.Utils.TimeUtils;
import com.theminequest.MineQuest.API.Quest.QuestDetails;

public class QuestReq implements QuestRequirement {

	private Type type;
	private String details;
	private QuestDetails quest;
	
	public QuestReq(Type type, QuestDetails quest, String details){
		this.type = type;
		this.details = details;
		this.quest = quest;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public String getDetails() {
		return details;
	}
	
	@Override
	public QuestDetails getQuest() {
		return quest;
	}

	@Override
	public boolean isSatisfied(Player player) {
		if (type == Type.NEVERDONE) {
			Map<String,Date> completed = QuestStatisticUtils.getQuests(player.getName(), LogStatus.COMPLETED);
			for (String q : completed.keySet())
				if (q.equals(quest.getProperty(QuestDetails.QUEST_NAME)))
					return false;
		} else if (type == Type.GIVE) {
			int id = Integer.parseInt(details);
			if (player.getItemInHand().getTypeId()!=id)
				return false;
		} else if (type == Type.GROUPSIZE) {
			QuestGroup gsg = Managers.getQuestGroupManager().get(player);
			if (gsg!=null){
				int size = Integer.parseInt(details);
				if (gsg.getMembers().size()>size)
					return false;
			}
		} else if (type == Type.ITEM) {
			String[] ivalues = details.split(":");
			int qty = Integer.parseInt(ivalues[0]);
			int ivalue = Integer.parseInt(ivalues[1]);
			short idamage = Short.parseShort(ivalues[2]);
			if (!player.getInventory().contains(new ItemStack(ivalue,qty,idamage)))
				return false;
		} else if (type == Type.BELOWLEVEL) {
			int level = Integer.parseInt(details);
			if (player.getLevel()>level)
				return false;
		} else if (type == Type.ABOVELEVEL) {
			int level = Integer.parseInt(details);
			if (player.getLevel()<level)
				return false;
		} else if (type == Type.MONEY) {
			if (MineQuest.economy!=null){
				double moneyvalue = Double.parseDouble(details);
				if (!MineQuest.economy.has(player.getName(),moneyvalue))
					return false;
			}
		} else if (type == Type.PERMISSION) {
			if (!player.hasPermission(details))
				return false;
		} else if (type == Type.PLAYER) {
			String[] playernames = details.split(",");
			for (String s : playernames){
				if (s.equalsIgnoreCase(player.getName()))
					return true;
			}
			return false;
		} else if (type == Type.PREREQ) {
			String[] requirements = details.split(",");
			boolean isSuccess = requirements[0].equalsIgnoreCase("S");
			String questToCheck = requirements[1];
			LogStatus ls = QuestStatisticUtils.hasQuest(player.getName(), questToCheck);
			if (ls == LogStatus.COMPLETED || ls == LogStatus.FAILED)
				return (isSuccess == (ls==LogStatus.COMPLETED));
			return false;
		} else if (type == Type.TIME) {
			String[] timezone = details.split("-");
			long firsttimeticks = TimeUtils.matchTime(timezone[0]);
			long secondtimeticks = TimeUtils.matchTime(timezone[1]);
			long worldtime = player.getLocation().getWorld().getTime();
			if (worldtime>=firsttimeticks && worldtime <= secondtimeticks)
				return true;
			else if (secondtimeticks < firsttimeticks) {
				if (worldtime >= firsttimeticks || worldtime <= secondtimeticks)
					return true;
			}
			return false;
		} else if (type == Type.WEATHER) {
			boolean downpour = details.equalsIgnoreCase("rain");
			if (player.getWorld().isThundering()!=downpour)
				return false;
		} else if (type == Type.WORLD) {
			if (!player.getWorld().getName().equals(details))
				return false;
		} else if (type == Type.DATE) {
			String[] range = details.split("|");
			try {
				Date before = DateFormat.getInstance().parse(range[0]);
				Date after = DateFormat.getInstance().parse(range[1]);
				Date current = Calendar.getInstance().getTime();
				if (current.after(before) && current.before(after))
					return true;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}



}
