package com.theminequest.bukkit.frontend.sign;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.theminequest.api.Managers;
import com.theminequest.api.platform.MQPlayer;
import com.theminequest.api.quest.QuestDetails;
import com.theminequest.api.quest.QuestDetailsUtils;
import com.theminequest.api.statistic.LogStatus;
import com.theminequest.api.statistic.QuestStatisticUtils;
import com.theminequest.api.statistic.QuestStatisticUtils.QSException;

public class QuestSign implements Listener {
	
	public QuestSign() {
		Managers.log("[QuestSign] Starting Sign Frontends...");
	}
	
	private boolean isQuestSign(Sign sign) {
		String[] line = sign.getLines();
		if ((line[1] != null) && line[1].contains("[Quest]"))
			return (line[2] != null);
		return false;
	}
	
	// Listeners For Sign interact and place.
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Action action = event.getAction();
		Block block = event.getClickedBlock();
		Player player = event.getPlayer();
		if ((block == null) || (block.getState() == null))
			return;
		if (!(block.getState() instanceof Sign))
			return;
		Sign sign = (Sign) block.getState();
		if (!isQuestSign(sign))
			return;
		if (!player.hasPermission("minequest.sign.takequest")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this!");
			return;
		}
		String quest_name = sign.getLine(2);
		QuestDetails d = Managers.getQuestManager().getDetails(quest_name);
		if (d == null) {
			player.sendMessage(ChatColor.RED + "Yikes! We can't find this quest anymore...");
			return;
		}
		
		if (QuestStatisticUtils.hasQuest(player.getName(), quest_name) == LogStatus.GIVEN) {
			player.sendMessage("You already have this quest! Check your given quests!");
			return;
		}
		
		if (QuestStatisticUtils.hasQuest(player.getName(), quest_name) == LogStatus.ACTIVE) {
			player.sendMessage("This quest is already running actively in the world!");
			return;
		}
		
		MQPlayer mqp = Managers.getPlatform().toPlayer(player);
		
		if (action == Action.RIGHT_CLICK_BLOCK) {
			player.sendMessage(QuestDetailsUtils.getOverviewString(d).split(QuestDetailsUtils.CODE_NEWLINE_SEQ));
			if (QuestDetailsUtils.getRequirementsMet(d, mqp))
				player.sendMessage("This quest is currently " + ChatColor.BOLD + ChatColor.GREEN + "available" + ChatColor.RESET + " to you - left click to accept!");
			else
				player.sendMessage("This quest is currently " + ChatColor.BOLD + ChatColor.RED + "not available" + ChatColor.RESET + " to you.");
		} else if (action == Action.LEFT_CLICK_BLOCK)
			if (QuestDetailsUtils.getRequirementsMet(d, mqp))
				try {
					QuestStatisticUtils.giveQuest(player.getName(), quest_name);
					player.sendMessage(ChatColor.GREEN + "Successfully added " + d.getProperty(QuestDetails.QUEST_DISPLAYNAME) + " to your quest list!");
				} catch (QSException e) {
					player.sendMessage("This quest doesn't seem to like you. (call an admin - something went wrong)");
					e.printStackTrace();
				}
			else
				player.sendMessage("This quest is currently " + ChatColor.BOLD + ChatColor.RED + "not available" + ChatColor.RESET + " to you.");
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onSignChangeEvent(SignChangeEvent event) {
		if (!event.getLine(1).equalsIgnoreCase("[Quest]"))
			return;
		Player player = event.getPlayer();
		Block block = event.getBlock();
		if (!player.hasPermission("minequest.sign.placesign")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to do this!");
			block.breakNaturally();
			return;
		}
		if (event.getLine(2).equalsIgnoreCase("")) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + "Must specify a quest!");
			block.breakNaturally();
			return;
		}
		if (Managers.getQuestManager().getDetails(event.getLine(2)) == null) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + "No such quest!");
			block.breakNaturally();
			return;
		}
		// oh, prettify it ;D
		event.setLine(1, ChatColor.GREEN + "[Quest]");
	}
}
