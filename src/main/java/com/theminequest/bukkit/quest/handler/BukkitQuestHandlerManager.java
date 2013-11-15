package com.theminequest.bukkit.quest.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.theminequest.api.quest.handler.QuestHandler;
import com.theminequest.api.quest.handler.QuestHandlerManager;
import com.theminequest.bukkit.impl.v1parser.InstancedHandler;
import com.theminequest.bukkit.impl.v1parser.WorldHandler;
import com.theminequest.common.quest.js.JsHandler;
import com.theminequest.common.quest.v1.V1Handler;

public class BukkitQuestHandlerManager implements QuestHandlerManager {
	
	private Map<String, QuestHandler<?>> handlers;
	
	public BukkitQuestHandlerManager() {
		handlers = new HashMap<String, QuestHandler<?>>();
		
		V1Handler v1 = new V1Handler();
		v1.addParser("instance", new InstancedHandler());
		v1.addParser("world", new WorldHandler());
		handlers.put("quest", v1);
		
		JsHandler js = new JsHandler();
		js.addParser("instance", new InstancedHandler());
		js.addParser("world", new WorldHandler());
		handlers.put("jsq", js);
	}
	
	@Override
	public void addQuestHandler(String endsWith, QuestHandler<?> handler) {
		handlers.put(endsWith.toLowerCase(), handler);
	}
	
	@Override
	public QuestHandler<?> getQuestHandler(String endsWith) {
		return handlers.get(endsWith.toLowerCase());
	}
	
	@Override
	public Set<String> getQuestHandlers() {
		return handlers.keySet();
	}
	
	@Override
	public QuestHandler<?> removeQuestHandler(String endsWith) {
		return handlers.remove(endsWith.toLowerCase());
	}
	
}
