# Get all quests available for this player
# %s - player name
SELECT * FROM MQ_QUEST WHERE (P_NAME = '%s') AND (ISCOMPLETED = 0);