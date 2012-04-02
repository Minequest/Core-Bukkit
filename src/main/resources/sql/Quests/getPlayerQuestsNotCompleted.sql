# Get all quests assigned, but not completed for this player
# %s - player name
SELECT * FROM MQ_QUEST WHERE (P_NAME = '%s') AND (ISCOMPLETED = 1);