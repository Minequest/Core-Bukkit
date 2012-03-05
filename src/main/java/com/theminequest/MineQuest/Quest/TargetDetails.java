package com.theminequest.MineQuest.Quest;

public class TargetDetails {

	public enum TargetType {
		AREATARGET,AREATARGETQUESTER,TEAMTARGET,TARGETTER,TARGETTEREDIT,RANDOMTARGET;
	}
	
	private long quest;
	private TargetType type;
	private String details;
	
	public TargetDetails(long questid, String details){
		quest = questid;
		String[] info = details.split(":");
		String type = info[0].toLowerCase();
		if (type.equals("areatarget")){
			this.type = TargetType.AREATARGET;
		}else if (type.equals("areatargetquester")){
			this.type = TargetType.AREATARGETQUESTER;
		}else if (type.equals("partytarget")||type.equals("teamtarget")){
			this.type = TargetType.TEAMTARGET;
		}else if (type.equals(""))
	}
	
}
