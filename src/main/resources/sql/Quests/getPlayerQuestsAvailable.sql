# Get all quests available for this player
# %s - player name
SELECT * FROM mq_quest WHERE (P_NAME = '%s') AND (ISCOMPLETED = 0);