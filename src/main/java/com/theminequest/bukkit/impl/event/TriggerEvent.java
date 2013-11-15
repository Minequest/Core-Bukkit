package com.theminequest.bukkit.impl.event;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.theminequest.api.CompleteStatus;
import com.theminequest.api.Managers;
import com.theminequest.api.group.Group;
import com.theminequest.api.quest.event.QuestEvent;
import com.theminequest.bukkit.frontend.cmd.CommandTriggerEvent;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Event",
		ident = "TriggerEvent",
		description = "Allow outside sources (via command/event) to trigger this event. This event can proceed to the next task based on the status of it, e.g. /mqtrigger playerName questName true/false will determine how this returns.",
		arguments = { "True Task", "False Task" },
		typeArguments = { DocArgType.INT, DocArgType.INT }
		)
public class TriggerEvent extends QuestEvent implements Listener {
	
	@Override
	public void setUpEvent() {
		Bukkit.getPluginManager().registerEvents(this, (JavaPlugin) Managers.getPlatform().getPlatformObject());
	}

	@Override
	public void cleanUpEvent() {
		HandlerList.unregisterAll(this);
	}

	private int trueTask;
	private int falseTask;
	private boolean proceed;
	private int nextTask;
	
	@Override
	public void setupArguments(String[] arguments) {
		trueTask = Integer.parseInt(arguments[0]);
		falseTask = Integer.parseInt(arguments[1]);
		proceed = false;
		nextTask = 0;
	}
	
	@EventHandler
	public void triggerEventCondition(CommandTriggerEvent event) {
		if (!getQuest().getDetails().getName().equals(event.getQuestName()))
			return;
		Group g = Managers.getGroupManager().get(getQuest());
		if (g.contains(Managers.getPlatform().getPlayer(event.getPlayerName()))) {
			nextTask = event.getResult() ? trueTask : falseTask;
			proceed = true;
		}
	}
	
	@Override
	public boolean conditions() {
		return proceed;
	}
	
	@Override
	public CompleteStatus action() {
		return CompleteStatus.SUCCESS;
	}
	
	@Override
	public Integer switchTask() {
		return nextTask;
	}
	
}
