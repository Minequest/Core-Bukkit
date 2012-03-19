/**
 * This file, TargetDetails.java, is part of MineQuest:
 * A full featured and customizable quest/mission system.
 * Copyright (C) 2012 The MineQuest Team
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 **/
package com.theminequest.MineQuest.Target;

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
		if (type.equals("areatarget"))
			this.type = TargetType.AREATARGET;
		else if (type.equals("areatargetquester"))
			this.type = TargetType.AREATARGETQUESTER;
		else if (type.equals("partytarget")||type.equals("teamtarget"))
			this.type = TargetType.TEAMTARGET;
		else if (type.equals("targetter"))
			this.type = TargetType.TARGETTER;
		else if (type.equals("targetteredit"))
			this.type = TargetType.TARGETTEREDIT;
		else if (type.equals("randomtarget"))
			this.type = TargetType.RANDOMTARGET;
	}
	
	public long getQuest(){
		return quest;
	}
	
	public TargetType getType(){
		return type;
	}
	
	public String getDetails(){
		return details;
	}
	
}
