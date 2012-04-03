# Changes:
# C_ID is now of type "text".
S:MySQL:ALTER TABLE mq_player modify C_ID text;
S:H2:ALTER TABLE mq_player ALTER COLUMN C_ID text;