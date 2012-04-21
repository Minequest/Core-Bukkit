package com.theminequest.MineQuest.Quest.Parser;

import java.util.List;

import com.theminequest.MineQuest.Editable.CertainBlockEdit;
import com.theminequest.MineQuest.Editable.CoordinateEdit;
import com.theminequest.MineQuest.Editable.Edit;
import com.theminequest.MineQuest.Editable.InsideAreaEdit;
import com.theminequest.MineQuest.Editable.ItemInHandEdit;
import com.theminequest.MineQuest.Editable.OutsideAreaEdit;
import com.theminequest.MineQuest.Quest.Quest;
import com.theminequest.MineQuest.Quest.QuestDescription;
import com.theminequest.MineQuest.Quest.QuestParser.QHandler;

public class EditHandler implements QHandler {

	@Override
	public void parseDetails(QuestDescription q, List<String> line) {
		int number = Integer.parseInt(line.get(0));
		String edittype = line.get(1);
		String d = "";
		for (int i=2; i<line.size(); i++){
			d += line.get(i);
			if (i!=line.size()-1)
				d+=":";
		}
		Edit e;
		if (edittype.equalsIgnoreCase("CanEdit"))
			e = new CoordinateEdit(number,Integer.parseInt(d.split(":")[3]),d);
		else if (edittype.equalsIgnoreCase("CanEditArea"))
			e = new InsideAreaEdit(number,Integer.parseInt(d.split(":")[6]),d);
		else if (edittype.equalsIgnoreCase("CanEditOutsideArea"))
			e = new OutsideAreaEdit(number,Integer.parseInt(d.split(":")[6]),d);
		else {
			int taskid = Integer.parseInt(line.get(2));
			d = "";
			for (int i=3; i<line.size(); i++){
				d += line.get(i);
				if (i!=line.size()-1)
					d+=":";
			}
			if (edittype.equalsIgnoreCase("CanEditTypesInHand"))
				e = new ItemInHandEdit(number,taskid,d);
			else
				e = new CertainBlockEdit(number,taskid,d);
		}
		q.editables.put(number, e);
	}

}
