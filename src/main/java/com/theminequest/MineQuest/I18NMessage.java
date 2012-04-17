package com.theminequest.MineQuest;

public enum I18NMessage {

	Cmd_INVALIDARGS ("Invalid arguments. See the help menu for arguments."),
	Cmd_NOSUCHPLAYER ("No such player!"),
	Cmd_NOTLEADER ("Not leader!"),
	Cmd_Party_INPARTY ("Already in party!"),
	Cmd_Party_NOPARTY ("Not in party!"),
	Cmd_Party_NOINVITE ("No pending invites..."),
	Cmd_Party_TARGETINVITESENT ("Invite sent to target!"),
	Cmd_Party_TARGETNOPARTY ("Target not in {any|your} party!"),
	Cmd_Party_TARGETINPARTY ("Target is already in a party!"),
	Cmd_Party_TARGETPENDING ("Target has a pending invite!"),
	Cmd_Party_ACCEPT ("Joined the party!"),
	Cmd_Party_CREATE ("Created and joined a new party!"),
	Cmd_Party_DISCARD ("Discarding party invite..."),
	Cmd_Party_KICK ("Kicked player: "),
	Cmd_Party_KICKTARGET ("Kicked off the party!"),
	Cmd_Party_LEAVE ("Departed the party!"),
	Cmd_Party_LIST ("Party List"),
	Cmd_Party_PROMOTE ("Promoted player to leader! You are no longer leader!"),
	Cmd_Party_PROMOTETARGET ("You are now leader!"),
	Cmd_Party_HELP ("Party Help"),
	Cmd_Party_HELPINVITE ("Accept pending invite."),
	Cmd_Party_HELPCREATE ("Create a new party."),
	Cmd_Party_HELPINVITETARGET ("Invite a player to the party."),
	Cmd_Party_HELPKICK ("Kick a player."),
	Cmd_Party_HELPLEAVE ("Leave the party."),
	Cmd_Party_HELPLIST ("List members and statistics."),
	Cmd_Party_HELPPROMOTE ("Give leader position to someone else."),
	Quest_NODESC ("No description available..."),
	Quest_ACCEPT ("Quest accepted!"),
	Quest_ABORT ("Quest aborted!"),
	Quest_COMPLETE ("You've successfully completed the quest!"),
	Quest_NOEDIT ("Nice try, but you can't edit this part of the world."),
	Top_Cat_MEOW ("Meow!");
	
	private final String description;
	private I18NMessage(String d){
		description = d;
	}
	
	public String getDescription(){
		return MineQuest.configuration.localizationConfig.getChatString(name(), description);
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return getDescription();
	}
	
}
