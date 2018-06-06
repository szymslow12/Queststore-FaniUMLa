CREATE TABLE IF NOT EXISTS users
(
id_user SERIAL PRIMARY KEY,
login TEXT,
user_password TEXT,
user_access TEXT
);
CREATE TABLE IF NOT EXISTS mentors
(
id_user INTEGER REFERENCES users(id_user),
first_name TEXT,
last_name TEXT,
email TEXT,
phone_number TEXT
);
CREATE TABLE IF NOT EXISTS admins
(
id_user INTEGER REFERENCES users(id_user),
first_name TEXT,
last_name TEXT,
email TEXT,
phone_number TEXT
);
CREATE TABLE IF NOT EXISTS codecoolers
(
id_user INTEGER REFERENCES users(id_user),
first_name TEXT,
last_name TEXT,
email TEXT,
phone_number TEXT,
coolcoins INTEGER,
level_of_exp TEXT,
id_class INTEGER
);
CREATE TABLE IF NOT EXISTS classes
(
id_class SERIAL PRIMARY KEY,
class_name TEXT
);
CREATE TABLE IF NOT EXISTS mentors_classes
(
id_mentorclass SERIAL PRIMARY KEY,
id_mentor INTEGER,
FOREIGN KEY (id_mentor) REFERENCES mentors(id_user),
id_class INTEGER,
FOREIGN KEY (id_class) REFERENCES classes(id_class)
);
CREATE TABLE IF NOT EXISTS store
(
id_artifact SERIAL PRIMARY KEY,
artifact_name TEXT,
category TEXT NOT NULL,
price INTEGER,
description TEXT
);
CREATE TABLE IF NOT EXISTS artifacts_codecooleres
(
id_art_codecooler SERIAL PRIMARY KEY,
id_codecooler INTEGER,
FOREIGN KEY (id_codecooler) REFERENCES codecoolers(id_user),
id_artifact INTEGER,
FOREIGN KEY (id_artifact) REFERENCES store(id_artifact)
);
CREATE TABLE IF NOT EXISTS artifacts_categories
(
id_category SERIAL PRIMARY KEY,
art_category_name TEXT
);
CREATE TABLE IF NOT EXISTS discards
(
id_discard SERIAL PRIMARY KEY,
discard_name TEXT,
id_artifact INTEGER,
amount INTEGER
);
CREATE TABLE IF NOT EXISTS discards_codecoolers
(
id_discardcodecooler SERIAL PRIMARY KEY,
id_discard INTEGER,
FOREIGN KEY (id_discard) REFERENCES discards(id_discard),
id_codecooler INTEGER,
FOREIGN KEY (id_codecooler) REFERENCES codecoolers(id_user)
);
CREATE TABLE IF NOT EXISTS quests
(
id_quest SERIAL PRIMARY KEY,
id_category INTEGER,
quest_name TEXT,
award INTEGER,
description TEXT
);
CREATE TABLE IF NOT EXISTS quests_codecoolers
(
id_questcodecooler SERIAL PRIMARY KEY,
id_quest INTEGER,
FOREIGN KEY (id_quest) REFERENCES quests(id_quest),
id_codecooler INTEGER,
FOREIGN KEY (id_codecooler) REFERENCES codecoolers(id_user)
);
CREATE TABLE IF NOT EXISTS quests_categories
(
id_quest_category SERIAL PRIMARY KEY,
quest_category_name TEXT
);
