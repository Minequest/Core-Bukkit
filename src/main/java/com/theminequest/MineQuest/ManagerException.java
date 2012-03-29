package com.theminequest.MineQuest;

public class ManagerException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4839674539656984977L;
	private ManagerReason managerReason;

	public ManagerException(ManagerReason arg0) {
		super(arg0.name());
		managerReason = arg0;
	}

	public ManagerException(Throwable arg0) {
		super(arg0);
		managerReason = ManagerReason.EXTERNALEXCEPTION;
	}

	public ManagerException(ManagerReason arg0, Throwable arg1) {
		super(arg0.name(), arg1);
		managerReason = arg0;
	}
	
	public ManagerReason getReason(){
		return managerReason;
	}
	
	public enum ManagerReason {
		NOTIMPLEMENTED, FAILED, INVALIDARGS, EXTERNALEXCEPTION;
	}

}
