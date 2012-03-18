# minequest initial database creation

# MineQuest Player Table (PlayerName, PlayerClass, PlayerLevel, PlayerEXP)
CREATE TABLE mq_player (P_NAME text, C_ID int, level int, exp bigint);

# MineQuest isQuestCompleted Table (PlayerName, QuestName, isCompleted)
CREATE TABLE mq_quest (P_NAME text, Q_ID text, isCompleted smallint(1));

# MineQuest NPC Table (NPCID, NPCName, NPCClass, NPCSkinURL, NPCCapeURL, NPCNearbyText, NPCGreetingText, NPCAboutText, NPCHealth, isVulnerable)
CREATE TABLE mq_npc (N_ID int, name text, C_ID int, skin text, cape text, nearby text, greeting longtext, about longtext, health int(20), isVulnerable smallint(1));

# MineQuest Abilities Table (PlayerName, AbilityID)
CREATE TABLE mq_ability (P_NAME text, A_ID text);