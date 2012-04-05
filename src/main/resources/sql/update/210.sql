# Changes:
# Players are now serialized.
DROP TABLE mq_player;
CREATE TABLE mq_player (P_NAME text, object BLOB);