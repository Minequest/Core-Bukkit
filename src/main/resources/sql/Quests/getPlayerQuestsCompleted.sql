# Get all quests assigned, but not completed for this player
# %s - player name
SELECT * FROM mq_quest WHERE (P_NAME = '%s') AND (ISCOMPLETED = 2);