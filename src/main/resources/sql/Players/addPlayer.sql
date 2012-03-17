# Adds a player to the SQL database.
# VALUES: %s should contain player ID and name (ID,NAME)
# the rest will set the respective values to 0.
INSERT INTO `mq_players` VALUES (%s,0,0,0,0)