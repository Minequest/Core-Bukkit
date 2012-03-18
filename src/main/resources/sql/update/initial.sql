# minequest initial database creation

CREATE TABLE `mq_players` (ID char(6), Name char(25), ClassName char(10) default 'none', Level char(6) default '1', Exp char(10) default '1');
CREATE TABLE `mq_quests` (ID char(6), Name char(25), QuestName char(25), Completed smallint(1) default '0');
CREATE TABLE `mq_npcs` (ID char(6), Name char(25), Class char(10), Health char(20), Vulnerable smallint(1) default '0');
CREATE TABLE `mq_abilities` (ID char(6), Name char(25), AbilityName char(25));