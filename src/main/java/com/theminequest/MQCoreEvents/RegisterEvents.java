package com.theminequest.MQCoreEvents;

import com.theminequest.MQCoreEvents.BasicEvents.CollectEvent;
import com.theminequest.MQCoreEvents.BasicEvents.DestroyEvent;
import com.theminequest.MQCoreEvents.BasicEvents.KillEvent;
import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.EventsAPI.EventManager;

public final class RegisterEvents {

	public static void RegisterEvents(){
		EventManager e = MineQuest.eventManager;
		e.registerEvent("CompleteQuestEvent", CompleteQuestEvent.class);
		e.registerEvent("LockWorldTimeEvent", LockWorldTimeEvent.class);
		e.registerEvent("MessageEvent", MessageEvent.class);
		e.registerEvent("PartyHealthEvent", PartyHealthEvent.class);
		e.registerEvent("TeamHealthEvent",PartyHealthEvent.class);
		e.registerEvent("QuestAvailableEvent",QuestAvailableEvent.class);
		e.registerEvent("QuestEvent", QuestEvent.class);
		e.registerEvent("RewardExpEvent",RewardExpEvent.class);
		e.registerEvent("RewardMoneyEvent",RewardMoneyEvent.class);
		e.registerEvent("RewardPermEvent",RewardPermEvent.class);
		
		e.registerEvent("CollectEvent", CollectEvent.class);
		e.registerEvent("DestroyEvent", DestroyEvent.class);
		e.registerEvent("KillEvent", KillEvent.class);
	}
	
}
