package com.theminequest.MineQuest.Frontend.Command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Backend.BackendFailedException;
import com.theminequest.MineQuest.Backend.GroupBackend;
import com.theminequest.MineQuest.Group.Group;
import com.theminequest.MineQuest.Group.GroupException;
import com.theminequest.MineQuest.Group.GroupException.GroupReason;
import com.theminequest.MineQuest.Player.PlayerDetails;
import com.theminequest.MineQuest.Utils.ChatUtils;

public class PartyCommandFrontend extends CommandFrontend {

	public PartyCommandFrontend() {
		super("party");
	}

	public Boolean accept(Player p, String[] args) {
		if (GroupBackend.teamID(p)!=-1){
			p.sendMessage(ChatColor.RED + localization.getString("party_INPARTY","You're already in a party..."));
			return false;
		}
		if (!GroupBackend.hasInvite(p)){
			p.sendMessage(ChatColor.RED + localization.getString("party_NOINVITE","No pending invites..."));
			return false;
		}
		try {
			GroupBackend.acceptInvite(p);
			p.sendMessage(ChatUtils.chatify(localization.getString("party_accept","Joined the team!")));
			return true;
		} catch (BackendFailedException e) {
			e.printStackTrace();
			p.sendMessage(ChatColor.GRAY + "ERR: " + e.getMessage());
			return false;
		}
	}

	public Boolean create(Player p, String[] args) {
		if (GroupBackend.teamID(p)!=-1){
			p.sendMessage(ChatColor.RED + localization.getString("party_INPARTY","You're already in a party..."));
			return false;
		}
		if (GroupBackend.hasInvite(p)){
			p.sendMessage(ChatColor.GRAY + localization.getString("party_create_discardinvite","Discarding pending invite..."));
		}
		try {
			GroupBackend.createTeam(p);
			p.sendMessage(ChatUtils.chatify(localization.getString("party_create","Created and joined the team!")));
			return true;
		} catch (BackendFailedException e) {
			e.printStackTrace();
			p.sendMessage(ChatColor.GRAY + "ERR: " + e.getMessage());
			return false;
		}
	}

	public Boolean info(Player p, String[] args) {
		if (GroupBackend.teamID(p)==-1){
			p.sendMessage(ChatColor.RED + localization.getString("party_NOPARTY","You can't use party commands without a party..."));
			return false;
		}
		if (args.length!=1){
			p.sendMessage(ChatColor.RED + localization.getString("party_INVALIDARGS","Wrong number of arguments."));
			return false;
		}

		List<Player> mates = GroupBackend.getCurrentGroup(p).getPlayers();
		Player lookup = Bukkit.getPlayerExact(args[0]);
		if (lookup==null){
			p.sendMessage(ChatColor.RED + localization.getString("party_NOSUCHPLAYER","No such player!"));
			return false;
		}
		if (!mates.contains(lookup)){
			p.sendMessage(ChatColor.RED + localization.getString("party_NOTINTEAM","Player is not in your team..."));
			return false;
		}
		Player mate = mates.get(mates.indexOf(lookup));

		// FIXME ARGHH.
		PlayerDetails details = MineQuest.playerManager.getPlayerDetails(mate);

		// TODO fill as more stuff comes in.
		int level = details.getLevel();

		List<String> messages = new ArrayList<String>();
		messages.add(ChatUtils.formatHeader(localization.getString("party_info","Player information: ") + lookup.getName()));
		messages.add(ChatColor.AQUA + localization.getString("party_info_displayname", "Display Name") + ": " + ChatColor.YELLOW + mate.getDisplayName());
		messages.add(ChatColor.AQUA + localization.getString("party_info_health", "Health") + ": " + ChatColor.YELLOW + mate.getHealth());
		messages.add(ChatColor.AQUA + localization.getString("party_info_level", "Level") + ": " + ChatColor.YELLOW + level);

		for (String m : messages)
			p.sendMessage(m);
		return true;
	}

	public Boolean invite(Player p, String[] args) {
		if (GroupBackend.teamID(p)==-1){
			p.sendMessage(ChatColor.RED + localization.getString("party_NOPARTY","You can't use party commands without a party..."));
			return false;
		}
		if (args.length!=1){
			p.sendMessage(ChatColor.RED + localization.getString("party_INVALIDARGS","Wrong number of arguments."));
			return false;
		}
		Group g = GroupBackend.getCurrentGroup(p);
		if (!g.getLeader().equals(p)){
			p.sendMessage(ChatColor.RED + localization.getString("party_NOTLEADER","not leader..."));
			return false;
		}

		Player mate = Bukkit.getPlayerExact(args[0]);
		if (mate==null){
			p.sendMessage(ChatColor.RED + localization.getString("party_NOSUCHPLAYER","No such player!"));
			return false;
		}
		if (GroupBackend.teamID(mate)!=-1){
			p.sendMessage(ChatColor.RED + localization.getString("party_INVITEEINPARTY","You're trying to invite someone already in a team."));
			return false;
		}
		try {
			g.invite(mate);
			p.sendMessage(ChatUtils.chatify(localization.getString("party_invite","Invited player!")));
			return true;
		} catch (GroupException e) {
			if (e.getReason()==GroupReason.ALREADYINTEAM){
				p.sendMessage(ChatColor.RED + localization.getString("party_INVITEEWAITING","Your invitee is waiting on another invite. Try again in a few seconds..."));
				return false;
			}
			p.sendMessage(ChatColor.GRAY + "ERR: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public Boolean kick(Player p, String[] args) {
		if (GroupBackend.teamID(p)==-1){
			p.sendMessage(ChatColor.RED + localization.getString("party_NOPARTY","You can't use party commands without a party..."));
			return false;
		}
		if (args.length!=1){
			p.sendMessage(ChatColor.RED + localization.getString("party_INVALIDARGS","Wrong number of arguments."));
			return false;
		}
		Group g = GroupBackend.getCurrentGroup(p);
		if (!g.getLeader().equals(p)){
			p.sendMessage(ChatColor.RED + localization.getString("party_NOTLEADER","not leader..."));
			return false;
		}
		Player mate = Bukkit.getPlayer(args[0]);
		if (!g.contains(mate)){
			p.sendMessage(ChatColor.RED + localization.getString("party_NOTINPARTY","Player isn't in the party..."));
			return false;
		}

		try {
			g.remove(mate);
			p.sendMessage(ChatUtils.chatify(localization.getString("party_kick","Kicked player!")));
			mate.sendMessage(ChatUtils.chatify(localization.getString("party_kick_mate","You were kicked out of the team by ")+p.getName()));
			return true;
		} catch (GroupException e) {
			p.sendMessage(ChatColor.GRAY + "ERR: " + e.getMessage());
			e.printStackTrace();
			return false;
		}

	}

	public Boolean leave(Player p, String[] args) {
		if (GroupBackend.teamID(p)==-1){
			p.sendMessage(ChatColor.RED + localization.getString("party_NOPARTY","You can't use party commands without a party..."));
			return false;
		}
		if (args.length!=0){
			p.sendMessage(ChatColor.RED + localization.getString("party_INVALIDARGS","Wrong number of arguments."));
			return false;
		}
		Group g = GroupBackend.getCurrentGroup(p);
		boolean leader = g.getLeader().equals(p);

		try {
			if (leader && g.getPlayers().size()!=1)
				g.setLeader(g.getPlayers().get(1));
			g.remove(p);
			p.sendMessage(ChatUtils.chatify(localization.getString("party_leave","Left the team.")));
			return true;
		} catch (GroupException e) {
			p.sendMessage(ChatColor.GRAY + "ERR: " + e.getMessage());
			e.printStackTrace();
			return false;
		}

	}
	
	public Boolean list(Player p, String[] args) {
		if (GroupBackend.teamID(p)==-1){
			p.sendMessage(ChatColor.RED + localization.getString("party_NOPARTY","You can't use party commands without a party..."));
			return false;
		}
		if (args.length!=0){
			p.sendMessage(ChatColor.RED + localization.getString("party_INVALIDARGS","Wrong number of arguments."));
			return false;
		}
		Group g = GroupBackend.getCurrentGroup(p);
		List<Player> members = g.getPlayers();
		List<String> messages = new ArrayList<String>();
		messages.add(ChatUtils.formatHeader(localization.getString("party_list","Party List") + " " + members.size() + "/" + g.getCapacity()));
		for (Player m : members) {
			if (g.getLeader().equals(m))
				messages.add(ChatColor.RED + m.getName());
			else
				messages.add(ChatColor.AQUA + m.getName());
		}
		
		for (String m : messages) {
			p.sendMessage(m);
		}
		
		return true;
	}
	
	public Boolean promote(Player p, String[] args) {
		if (GroupBackend.teamID(p)==-1){
			p.sendMessage(ChatColor.RED + localization.getString("party_NOPARTY","You can't use party commands without a party..."));
			return false;
		}
		if (args.length!=1){
			p.sendMessage(ChatColor.RED + localization.getString("party_INVALIDARGS","Wrong number of arguments."));
			return false;
		}
		Group g = GroupBackend.getCurrentGroup(p);
		if (!g.getLeader().equals(p)){
			p.sendMessage(ChatColor.RED + localization.getString("party_NOTLEADER","not leader..."));
			return false;
		}
		Player mate = Bukkit.getPlayer(args[0]);
		if (!g.contains(mate)){
			p.sendMessage(ChatColor.RED + localization.getString("party_NOTINPARTY","Player isn't in the party..."));
			return false;
		}
		
		try {
			g.setLeader(mate);
			mate.sendMessage(ChatUtils.chatify(localization.getString("party_promote_mate","You've been promoted to leader!")));
			p.sendMessage(ChatUtils.chatify(localization.getString("party_promote","Promoted player; depromoted yourself.")));
			return true;
		} catch (GroupException e) {
			p.sendMessage(ChatColor.GRAY + "ERR: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean help(Player p, String[] args) {
		/*
		 * accept
		 * create
		 * 
		 * info
		 * invite
		 * kick
		 * leave
		 * list
		 * promote
		 */

		List<String> messages = new ArrayList<String>();
		Group g = null;
		boolean invite = GroupBackend.hasInvite(p);
		boolean isLeader = false;
		if (GroupBackend.teamID(p)!=-1){
			g = GroupBackend.getCurrentGroup(p);
			isLeader = g.getLeader().equals(p);
		}

		messages.add(ChatUtils.formatHeader(localization.getString("party_help", "Party Commands")));
		if (g==null){
			if (invite)
				messages.add(ChatUtils.formatHelp("party accept", localization.getString("party_help_accept","Accept pending party invite. QUICK.")));
			else
				messages.add(ChatColor.GRAY + localization.getString("party_NOINVITE","No pending invites..."));
			messages.add(ChatUtils.formatHelp("party create", localization.getString("party_help_create","Create a party.")));
			messages.add(ChatColor.AQUA + localization.getString("party_NOPARTY","You can't use party commands without a party..."));
		} else {
			messages.add(ChatUtils.formatHelp("party info <name>","Get information on a party member."));
			if (isLeader){
				messages.add(ChatUtils.formatHelp("party invite <name>", localization.getString("party_help_invite","Invite a player.")));
				messages.add(ChatUtils.formatHelp("party kick <name>", localization.getString("party_help_kick","Kick a player.")));
			} else {
				messages.add(ChatColor.GRAY + "[party invite] " + localization.getString("party_NOTLEADER","not leader..."));
				messages.add(ChatColor.GRAY + "[party kick] " + localization.getString("party_NOTLEADER","not leader..."));
			}
			messages.add(ChatUtils.formatHelp("party leave",localization.getString("party_help_leave","Leave the current party.")));
			messages.add(ChatUtils.formatHelp("party list", localization.getString("party_help_list","List members in your party.")));
			if (isLeader)
				messages.add(ChatUtils.formatHelp("party promote <name>", localization.getString("party_help_promote","Promote someone else to leader.")));
			else
				messages.add(ChatColor.GRAY + "[party promote] " + localization.getString("party_NOTLEADER","not leader..."));
		}

		for (String s : messages)
			p.sendMessage(s);
		return true;
	}

	@Override
	public boolean allowConsole() {
		return false;
	}

}
