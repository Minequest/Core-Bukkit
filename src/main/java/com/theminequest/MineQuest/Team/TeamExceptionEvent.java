package com.theminequest.MineQuest.Team;

public class TeamExceptionEvent extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1073787842559540982L;
	
	private Cause cause;

	public TeamExceptionEvent(Cause c){
		super(c.name());
		cause = c;
	}

	public TeamExceptionEvent(Throwable arg0) {
		super(Cause.EXTERNALEXCEPTION.name(), arg0);
		cause = Cause.EXTERNALEXCEPTION;
		// TODO Auto-generated constructor stub
	}

	public TeamExceptionEvent(Cause c, Throwable arg1) {
		super(c.name(), arg1);
		cause = c;
		// TODO Auto-generated constructor stub
	}
	
	public Cause getReason(){
		return cause;
	}
	
	public enum Cause {
		BADCAPACITY, OVERCAPACITY, ALREADYONQUEST, NOQUEST,
		ALREADYINTEAM, NOTONTEAM, EXTERNALEXCEPTION;
	}

}
