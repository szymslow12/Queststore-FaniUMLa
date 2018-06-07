CREATE TABLE IF NOT EXISTS users
(
id_user SERIAL PRIMARY KEY,
login TEXT,
user_password TEXT,
user_access TEXT,
first_name TEXT,
last_name TEXT,
email TEXT,
phone_number TEXT
);
CREATE TABLE IF NOT EXISTS mentors
(
id_user INTEGER REFERENCES users(id_user)
ON DELETE CASCADE ON UPDATE NO ACTION,
id_mentor SERIAL PRIMARY KEY
);
CREATE TABLE IF NOT EXISTS admins
(
id_user INTEGER REFERENCES users(id_user)
ON DELETE CASCADE ON UPDATE NO ACTION,
id_admin SERIAL PRIMARY KEY
);
CREATE TABLE IF NOT EXISTS levels
(
id_level SERIAL PRIMARY KEY,
name_level TEXT
);
CREATE TABLE IF NOT EXISTS codecoolers
(
id_user INTEGER REFERENCES users(id_user)
ON DELETE CASCADE ON UPDATE NO ACTION,
id_codecooler SERIAL PRIMARY KEY,
coolcoins INTEGER,
id_level INTEGER REFERENCES levels(id_level)
ON DELETE CASCADE ON UPDATE NO ACTION,
id_class INTEGER
);
CREATE TABLE IF NOT EXISTS classes
(
id_class SERIAL PRIMARY KEY,
class_name TEXT
);
CREATE TABLE IF NOT EXISTS mentors_classes
(
id_mentor INTEGER,
FOREIGN KEY (id_mentor) REFERENCES mentors(id_mentor)
ON DELETE CASCADE ON UPDATE NO ACTION,
id_class INTEGER,
FOREIGN KEY (id_class) REFERENCES classes(id_class)
ON DELETE CASCADE ON UPDATE NO ACTION,
PRIMARY KEY(id_mentor, id_class)
);
CREATE TABLE IF NOT EXISTS artCategories
(
id_category SERIAL PRIMARY KEY,
art_category_name TEXT
);
CREATE TABLE IF NOT EXISTS artifacts
(
id_artifact SERIAL PRIMARY KEY,
artifact_name TEXT,
category_id INTEGER NOT NULL REFERENCES artCategories(id_category)
ON DELETE CASCADE ON UPDATE NO ACTION,
price INTEGER,
description TEXT
);
CREATE TABLE IF NOT EXISTS artifacts_codecooleres
(
id_codecooler INTEGER,
FOREIGN KEY (id_codecooler) REFERENCES codecoolers(id_codecooler)
ON DELETE CASCADE ON UPDATE NO ACTION,
id_artifact INTEGER,
FOREIGN KEY (id_artifact) REFERENCES artifacts(id_artifact)
ON DELETE CASCADE ON UPDATE NO ACTION,
PRIMARY KEY(id_codecooler, id_artifact),
quantity INTEGER
);
CREATE TABLE IF NOT EXISTS groups
(
id_group SERIAL PRIMARY KEY,
target TEXT
);
CREATE TABLE IF NOT EXISTS groups_codecoolers
(
id_group INTEGER,
FOREIGN KEY (id_group) REFERENCES groups(id_group)
ON DELETE CASCADE ON UPDATE NO ACTION,
id_codecooler INTEGER,
FOREIGN KEY (id_codecooler) REFERENCES codecoolers(id_codecooler)
ON DELETE CASCADE ON UPDATE NO ACTION,
id_artifact INTEGER,
FOREIGN KEY (id_artifact) REFERENCES artifacts(id_artifact)
ON DELETE CASCADE ON UPDATE NO ACTION,
PRIMARY KEY(id_group, id_codecooler)
);
CREATE TABLE IF NOT EXISTS questCategories
(
id_quest_category SERIAL PRIMARY KEY,
quest_category_name TEXT
);
CREATE TABLE IF NOT EXISTS quests
(
id_quest SERIAL PRIMARY KEY,
id_category INTEGER,
FOREIGN KEY (id_category) REFERENCES questCategories(id_quest_category)
ON DELETE CASCADE ON UPDATE NO ACTION,
quest_name TEXT,
award INTEGER,
description TEXT
);
CREATE TABLE IF NOT EXISTS quests_codecoolers
(
id_quest INTEGER,
FOREIGN KEY (id_quest) REFERENCES quests(id_quest)
ON DELETE CASCADE ON UPDATE NO ACTION,
id_codecooler INTEGER,
FOREIGN KEY (id_codecooler) REFERENCES codecoolers(id_codecooler)
ON DELETE CASCADE ON UPDATE NO ACTION,
PRIMARY KEY(id_quest, id_codecooler)
);

