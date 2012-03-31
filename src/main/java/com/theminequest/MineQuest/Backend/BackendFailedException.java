package com.theminequest.MineQuest.Backend;

public class BackendFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2873994917728725961L;

	private BackendReason backendReason;
	
	/**
	 * @param arg0
	 * @param arg1
	 */
	public BackendFailedException(BackendReason arg0, Throwable arg1) {
		super(arg0.name(), arg1);
		backendReason = arg0;
	}

	/**
	 * @param arg0
	 */
	public BackendFailedException(BackendReason arg0) {
		super(arg0.name());
		backendReason = arg0;
	}

	/**
	 * @param arg0
	 */
	public BackendFailedException(Throwable arg0) {
		super(BackendReason.EXTERNALEXCEPTION.name(), arg0);
		backendReason = BackendReason.EXTERNALEXCEPTION;
	}
	
	public BackendReason getReason(){
		return backendReason;
	}
	
	public enum BackendReason {
		MANAGEREXCEPTION, SQL, NOTIMPLEMENTED, FAILED, INVALIDARGS, EXTERNALEXCEPTION,
		// Group stuff
		NOTONTEAM, ALREADYONTEAM, NOINVITE, FULLORINQUEST,
		// Quest stuff
		NOTHAVEQUEST, ALREADYHAVEQUEST, UNREPEATABLEQUEST;
	}

}
