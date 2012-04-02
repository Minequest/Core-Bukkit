# Regive Repeatable Quest
# first %s: Player Name
# second %s: Quest Name
UPDATE mq_quest SET ISCOMPLETED = 0 WHERE P_NAME = '%s' AND Q_ID = '%s'