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
package com.theminequest.MQCoreEvents;

import com.theminequest.MQCoreEvents.BasicEvents.CollectEvent;
import com.theminequest.MQCoreEvents.BasicEvents.DestroyEvent;
import com.theminequest.MQCoreEvents.BasicEvents.KillEvent;
import com.theminequest.MQCoreEvents.BasicEvents.NRCollectEvent;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Events.EventManager;

public final class RegisterEvents {

	public static void registerEvents(){
		EventManager e = Managers.getEventManager();
		// start QuestEvent
		e.addEvent("QuestEvent", GenericQuestEvent.class);
		e.addEvent("TaskEvent", TaskEvent.class);
		// end QuestEvent
		e.addEvent("CompleteQuestEvent", CompleteQuestEvent.class);
		e.addEvent("QuestAvailableEvent", QuestAvailableEvent.class);
		e.addEvent("LockWorldTimeEvent", LockWorldTimeEvent.class);
		e.addEvent("MessageEvent", MessageEvent.class);
		e.addEvent("PartyHealthEvent", PartyHealthEvent.class);
		e.addEvent("RewardMoneyEvent",RewardMoneyEvent.class);
		e.addEvent("RewardPermEvent",RewardPermEvent.class);
		e.addEvent("RewardItemEvent", RewardItemEvent.class);
		e.addEvent("RewardCmdEvent", RewardCmdEvent.class);
		
		e.addEvent("CollectEvent", CollectEvent.class);
		e.addEvent("NRCollectEvent", NRCollectEvent.class);
		e.addEvent("DestroyEvent", DestroyEvent.class);
		e.addEvent("KillEvent", KillEvent.class);
		
		e.addEvent("RewardEnchanted", RewardEnchanted.class);
		e.addEvent("RewardDamaged", RewardDamaged.class);
		
	}
	
}
