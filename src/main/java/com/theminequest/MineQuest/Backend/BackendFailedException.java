package com.theminequest.MineQuest.Backend;

public class BackendFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2873994917728725961L;

	private Reason reason;
	
	/**
	 * @param arg0
	 * @param arg1
	 */
	public BackendFailedException(Reason arg0, Throwable arg1) {
		super(arg0.name(), arg1);
		reason = arg0;
	}

	/**
	 * @param arg0
	 */
	public BackendFailedException(Reason arg0) {
		super(arg0.name());
		reason = arg0;
	}

	/**
	 * @param arg0
	 */
	public BackendFailedException(Throwable arg0) {
		super(Reason.EXTERNALEXCEPTION.name(), arg0);
		reason = Reason.EXTERNALEXCEPTION;
	}
	
	public Reason getReason(){
		return reason;
	}
	
	public enum Reason {
		MANAGEREXCEPTION, SQL, NOTIMPLEMENTED, FAILED, INVALIDARGS, EXTERNALEXCEPTION,
		// Group stuff
		NOTONTEAM, ALREADYONTEAM,
		// Quest stuff
		NOTHAVEQUEST, ALREADYHAVEQUEST, UNREPEATABLEQUEST;
	}

}
