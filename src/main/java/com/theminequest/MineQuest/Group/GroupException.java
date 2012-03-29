package com.theminequest.MineQuest.Group;

public class GroupException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1073787842559540982L;
	
	private GroupReason groupReason;

	public GroupException(GroupReason c){
		super(c.name());
		groupReason = c;
	}

	public GroupException(Throwable arg0) {
		super(GroupReason.EXTERNALEXCEPTION.name(), arg0);
		groupReason = GroupReason.EXTERNALEXCEPTION;
		// TODO Auto-generated constructor stub
	}

	public GroupException(GroupReason c, Throwable arg1) {
		super(c.name(), arg1);
		groupReason = c;
		// TODO Auto-generated constructor stub
	}
	
	public GroupReason getReason(){
		return groupReason;
	}
	
	public enum GroupReason {
		BADCAPACITY, OVERCAPACITY, ALREADYONQUEST, INSIDEQUEST, NOTINSIDEQUEST, NOQUEST,
		ALREADYINTEAM, NOTONTEAM, NOLOCATIONS, UNFINISHEDQUEST, EXTERNALEXCEPTION;
	}

}
