package com.theminequest.MineQuest.Quest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.TreeMap;

import org.bukkit.ChatColor;

import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.Editable.CertainBlockEdit;
import com.theminequest.MineQuest.Editable.CoordinateEdit;
import com.theminequest.MineQuest.Editable.Edit;
import com.theminequest.MineQuest.Editable.InsideAreaEdit;
import com.theminequest.MineQuest.Editable.ItemInHandEdit;
import com.theminequest.MineQuest.Editable.OutsideAreaEdit;
import com.theminequest.MineQuest.Target.TargetDetails;
import com.theminequest.MineQuest.Tasks.Task;

public class QuestParser {
	
	protected static void parseDefinition(Quest q) throws FileNotFoundException{
		LinkedHashMap<Integer, Task> tasks = new LinkedHashMap<Integer, Task>();
		LinkedHashMap<Integer, String> events = new LinkedHashMap<Integer, String>();
		LinkedHashMap<Integer, TargetDetails> targets = new LinkedHashMap<Integer, TargetDetails>();
		LinkedHashMap<Integer, Edit> editables = new LinkedHashMap<Integer,Edit>();
		File f = new File(MineQuest.questManager.locationofQuests + File.separator + q.questname
				+ ".quest");
		Scanner filereader = new Scanner(f);
		while (filereader.hasNextLine()) {
			String nextline = filereader.nextLine();
			ArrayList<String> ar = (ArrayList<String>) Arrays.asList(nextline
					.split(":"));
			String type = ar.get(0).toLowerCase();
			if (type.equals("name"))
				q.displayname = ar.get(1);
			else if (type.equals("repeatable"))
				q.questRepeatable = (ar.get(1).equals("true"));
			else if (type.equals("reset"))
				q.spawnReset = (ar.get(1).equals("true"));
			else if (type.equals("spawn")) {
				if (!ar.get(1).equals(""))
					q.spawnPoint[0] = Double.parseDouble(ar.get(1));
				if (!ar.get(2).equals(""))
					q.spawnPoint[1] = Double.parseDouble(ar.get(2));
				if (!ar.get(3).equals(""))
					q.spawnPoint[2] = Double.parseDouble(ar.get(3));
			} else if (type.equals("areapreserve")) {
				if (!ar.get(1).equals(""))
					q.areaPreserve[0] = Double.parseDouble(ar.get(1));
				if (!ar.get(2).equals(""))
					q.areaPreserve[1] = Double.parseDouble(ar.get(2));
				if (!ar.get(3).equals(""))
					q.areaPreserve[2] = Double.parseDouble(ar.get(3));
				if (!ar.get(4).equals(""))
					q.areaPreserve[3] = Double.parseDouble(ar.get(4));
				if (!ar.get(5).equals(""))
					q.areaPreserve[4] = Double.parseDouble(ar.get(5));
				if (!ar.get(6).equals(""))
					q.areaPreserve[5] = Double.parseDouble(ar.get(6));
			} else if (type.equals("editmessage"))
				q.editMessage = ChatColor.GRAY + ar.get(1);
			else if (type.equals("world"))
				q.world = ar.get(1);
			else if (type.equals("loadworld")) {
				// I say YES to instances.
				q.loadworld = true;
				q.world = ar.get(2);
				// I do NOT care about QuestArea, because
				// I simply delete the world when done.
			} else if (type.equals("event")) {
				int number = Integer.parseInt(ar.get(1));
				// T = targeted event
				boolean targetedevent = false;
				if (ar.get(2).equals("T")) {
					ar.remove(2);
					targetedevent = true;
				}
				String eventname = ar.get(2);
				String details = "";
				if (targetedevent)
					details += "T:";
				for (int i = 3; i < ar.size(); i++) {
					details += ar.get(i);
					if (i < ar.size() - 1) {
						details += ":";
					}
				}
				// final result: "eventname:T:details"
				events.put(number, eventname + ":" + details);
			} else if (type.equals("target")) {
				int number = Integer.parseInt(ar.get(1));
				String d = "";
				for (int i=2; i<ar.size(); i++){
					d += ar.get(i);
					if (i!=ar.size()-1)
						d+=":";
				}
				targets.put(number, new TargetDetails(q.questid,d));
			} else if (type.equals("edit")) {
				int number = Integer.parseInt(ar.get(1));
				String edittype = ar.get(2);
				String d = "";
				for (int i=3; i<ar.size(); i++){
					d += ar.get(i);
					if (i!=ar.size()-1)
						d+=":";
				}
				Edit e;
				if (edittype.equalsIgnoreCase("CanEdit"))
					e = new CoordinateEdit(q.questid,number,Integer.parseInt(d.split(":")[3]),d);
				else if (edittype.equalsIgnoreCase("CanEditArea"))
					e = new InsideAreaEdit(q.questid,number,Integer.parseInt(d.split(":")[6]),d);
				else if (edittype.equalsIgnoreCase("CanEditOutsideArea"))
					e = new OutsideAreaEdit(q.questid,number,Integer.parseInt(d.split(":")[6]),d);
				else {
					int taskid = Integer.parseInt(ar.get(3));
					d = "";
					for (int i=4; i<ar.size(); i++){
						d += ar.get(i);
						if (i!=ar.size()-1)
							d+=":";
					}
					if (edittype.equalsIgnoreCase("CanEditTypesInHand"))
						e = new ItemInHandEdit(q.questid,number,taskid,d);
					else
						e = new CertainBlockEdit(q.questid,number,taskid,d);
				}
				editables.put(number, e);
			}
		}
		q.tasks = new TreeMap<Integer, Task>(tasks);
		q.events = new TreeMap<Integer, String>(events);
		q.targets = new TreeMap<Integer, TargetDetails>(targets);
		q.editables = new TreeMap<Integer, Edit>(editables);
	}

}
