package com.theminequest.MineQuest.Editable;

public class InsideAreaEdit extends AreaEdit {

	public InsideAreaEdit(long qid, int eid, int tid, String d) {
		super(qid, eid, tid, d);
	}

	@Override
	public boolean isInside() {
		return true;
	}

}
