package com.theminequest.MQCoreEvents;

import com.theminequest.MQCoreEvents.BasicEvents.CollectEvent;
import com.theminequest.MQCoreEvents.BasicEvents.DestroyEvent;
import com.theminequest.MQCoreEvents.BasicEvents.KillEvent;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Events.EventManager;

public final class RegisterEvents {

	public static void registerEvents(){
		EventManager e = Managers.getEventManager();
		e.addEvent("QuestEvent", GenericQuestEvent.class);
		e.addEvent("CompleteQuestEvent", CompleteQuestEvent.class);
		e.addEvent("QuestAvailableEvent", QuestAvailableEvent.class);
		e.addEvent("LockWorldTimeEvent", LockWorldTimeEvent.class);
		e.addEvent("MessageEvent", MessageEvent.class);
		e.addEvent("PartyHealthEvent", PartyHealthEvent.class);
		e.addEvent("QuestGivenEvent",QuestAvailableEvent.class);
		e.addEvent("TaskEvent", TaskEvent.class);
		e.addEvent("RewardMoneyEvent",RewardMoneyEvent.class);
		e.addEvent("RewardPermEvent",RewardPermEvent.class);
		e.addEvent("RewardItemEvent", RewardItemEvent.class);
		e.addEvent("RewardCmdEvent", RewardCmdEvent.class);
		
		e.addEvent("CollectEvent", CollectEvent.class);
		e.addEvent("DestroyEvent", DestroyEvent.class);
		e.addEvent("KillEvent", KillEvent.class);
	}
	
}
