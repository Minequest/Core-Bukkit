package com.theminequest.MineQuest.Group;

public class GroupException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1073787842559540982L;
	
	private Cause cause;

	public GroupException(Cause c){
		super(c.name());
		cause = c;
	}

	public GroupException(Throwable arg0) {
		super(Cause.EXTERNALEXCEPTION.name(), arg0);
		cause = Cause.EXTERNALEXCEPTION;
		// TODO Auto-generated constructor stub
	}

	public GroupException(Cause c, Throwable arg1) {
		super(c.name(), arg1);
		cause = c;
		// TODO Auto-generated constructor stub
	}
	
	public Cause getReason(){
		return cause;
	}
	
	public enum Cause {
		BADCAPACITY, OVERCAPACITY, ALREADYONQUEST, INSIDEQUEST, NOTINSIDEQUEST, NOQUEST,
		ALREADYINTEAM, NOTONTEAM, NOLOCATIONS, UNFINISHEDQUEST, EXTERNALEXCEPTION;
	}

}
