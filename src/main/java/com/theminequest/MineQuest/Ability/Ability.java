package com.theminequest.MineQuest.Ability;

import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.commons.ChatColor;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Player.PlayerDetails;
import com.theminequest.MineQuest.Player.PlayerManager;
import com.theminequest.MineQuest.Quest.QuestManager;

public abstract class Ability implements Listener {
	
	/**
	 * Give this ability a name, please?
	 * @return ability name
	 */
	public abstract String getName();

	/**
	 * How much mana (or endurance) this ability uses.<br>
	 * Remember that the mana of a person is (base mana)*level.
	 * @return % of total mana of a level 1 person (0-100) should be taken
	 */
	public abstract int getMana();
	
	/**
	 * Cooldown time after using this ability, in seconds.
	 * @return cooldown time in seconds.
	 */
	public abstract int getCooldown();
	
	/**
	 * Abilities are listeners for all events. When an event
	 * is called, abilities need to do something with it.<br>
	 * If this is the event the ability is looking for, i.e.
	 * the player is right, the event is the BlockEvent that
	 * you are looking for, and everything is in order to
	 * perform the event, return true.<br>
	 * <b>Hint</b>: For a specific event, use <i>instanceof</i>
	 * as an if statement to check if the event is the one you
	 * want.
	 * @param e Event caught.
	 * @return string with details for execution,
	 * (or {@link null} for wrong event/something wrong).
	 */
	public abstract String isRightEvent(Event e);
	
	/**
	 * Execute the event given the parameters.
	 * @param details Execution details
	 */
	public abstract void executeEvent(String details);
	
	/**
	 * Quests can disallow certain abilities.
	 * If the quest refuses to allow this event to happen,
	 * this will return false.
	 * @param p Player Name
	 */
	public boolean questAllow(Player p){
		long currentquest = PlayerManager.getPlayerDetails(p).getQuest();
		// outside the quest, of course you can use abilities
		if (currentquest==-1)
			return true;
		// inside the quest...
		List<String> abilities = QuestManager.getQuest(currentquest).getDisallowedAbilities();
		for (String s : abilities){
			if (s.equalsIgnoreCase(getName()))
				return false;
		}
		return true;
	}
	
	@EventHandler
	public void onEventCaught(Event e){
		String result = isRightEvent(e);
		if (result!=null){
			final Player p;
			try {
				Method m = e.getClass().getMethod("getPlayer");
				p = (Player) m.invoke(e);
			} catch (Exception ex){
				// ignore... no player did this, obviously,
				// or something is seriously screwed up
				return;
			}
			PlayerDetails details = PlayerManager.getPlayerDetails(p);
			if (details.abilitiesCoolDown.containsKey(this)){
				long currentseconds = System.currentTimeMillis()*1000;
				long timeelapsed = currentseconds-details.abilitiesCoolDown.get(this);
				if (timeelapsed<getCooldown()){
					p.sendMessage(ChatColor.YELLOW+"Ability " + getName() + " is recharging... "
							+ ChatColor.GRAY + "(" +(getCooldown()-timeelapsed)+ " s)");
				}
			}
			if (details.getAbilitiesEnabled() && questAllow(p)){
				details.modifyManaBy(-1*getMana());
				executeEvent(result);
				details.abilitiesCoolDown.put(this, System.currentTimeMillis()*1000);
				final Ability a = this;
				Bukkit.getScheduler().scheduleAsyncDelayedTask(MineQuest.activePlugin,
						new Runnable(){

							@Override
							public void run() {
								Bukkit.getPluginManager().callEvent(new AbilityRefreshedEvent(a,p));
							}
					
				}, 20*getCooldown());
			}
		}
	}
	
}
