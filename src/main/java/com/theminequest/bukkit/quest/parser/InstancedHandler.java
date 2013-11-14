package com.theminequest.bukkit.quest.parser;

import java.util.List;

import com.theminequest.api.quest.QuestDetails;
import com.theminequest.bukkit.BukkitPlatform;
import com.theminequest.common.quest.v1.V1Parser;
import com.theminequest.doc.DocArgType;
import com.theminequest.doc.V1Documentation;

@V1Documentation(
		type = "Instance",
		description = "Define an instanced dungeon world.",
		arguments = { "World", "Type (normal|nether|end)", "Mod (flat|large)" },
		typeArguments = { DocArgType.STRING, DocArgType.STRING, DocArgType.STRING }
		)
public class InstancedHandler implements V1Parser {
	
	@Override
	public void handle(QuestDetails q, List<String> line) {
		q.setProperty(QuestDetails.QUEST_LOADWORLD, true);
		q.setProperty(QuestDetails.QUEST_WORLD, line.get(0));
		
		int flags = 0x00000000;
		
		if (line.size() > 1) {
			String env = line.get(1);
			switch (env.toLowerCase()) {
			case "nether":
			case "1":
			case "true":
				flags = flags | BukkitPlatform.NETHER_FLAG;
				break;
			case "end":
				flags = flags | BukkitPlatform.END_FLAG;
				break;
			}
			
			if (line.size() > 2) {
				String type = line.get(2);
				switch (type.toLowerCase()) {
				case "flat":
					flags = flags | BukkitPlatform.FLAT_FLAG;
					break;
				case "large":
				case "largebiome":
				case "largebiomes":
					flags = flags | BukkitPlatform.LARGE_BIOME_FLAG;
					break;
				}
			}
		}
		
		q.setProperty(QuestDetails.QUEST_WORLDFLAGS, flags);
	}
	
}
