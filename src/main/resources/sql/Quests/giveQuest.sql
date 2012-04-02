# Give a quest to a player
# first %s : player name
# second %s : quest name
# the third value is for completion status:
#   0: Quest is available.
#   1: Quest has been accepted.
#   2: Quest has been completed.
INSERT INTO mq_quest VALUES ('%s', '%s', 0)