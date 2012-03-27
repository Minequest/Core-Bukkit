package com.theminequest.MineQuest;

public class ManagerException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4839674539656984977L;
	private Reason reason;

	public ManagerException(Reason arg0) {
		super(arg0.name());
		reason = arg0;
	}

	public ManagerException(Throwable arg0) {
		super(arg0);
		reason = Reason.EXTERNALEXCEPTION;
	}

	public ManagerException(Reason arg0, Throwable arg1) {
		super(arg0.name(), arg1);
		reason = arg0;
	}
	
	public Reason getReason(){
		return reason;
	}
	
	public enum Reason {
		NOTIMPLEMENTED, FAILED, INVALIDARGS, EXTERNALEXCEPTION;
	}

}
