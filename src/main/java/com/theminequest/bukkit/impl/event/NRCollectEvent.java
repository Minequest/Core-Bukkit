package com.theminequest.bukkit.impl.event;

import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Event",
		ident = "NRCollectEvent",
		description = "Request quest owner to collect items, but do NOT remove them afterwards.",
		arguments = { "Task to Activate on completion", "ITEM1,ITEM2,etc", "AMT1,AMT2,etc" },
		typeArguments = { DocArgType.INT, DocArgType.STRING, DocArgType.STRING }
		)
public class NRCollectEvent extends CollectEvent {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theminequest.MineQuest.Events.QEvent#parseDetails(java.lang.String[])
	 * Details:
	 * [0]: task to incur upon completion
	 * [1]: itemids;
	 * [2]: totaltocollect
	 */
	@Override
	public void setupArguments(String[] details) {
		collectItems = false;
		super.setupArguments(details);
	}
}
