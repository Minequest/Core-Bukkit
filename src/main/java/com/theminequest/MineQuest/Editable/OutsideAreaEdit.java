package com.theminequest.MineQuest.Editable;

public class OutsideAreaEdit extends AreaEdit {

	public OutsideAreaEdit(long qid, int eid, int tid, String d) {
		super(qid, eid, tid, d);
	}

	@Override
	public boolean isInside() {
		return false;
	}

}
