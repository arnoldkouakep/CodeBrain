
drop table "BRAIN".LEVELS;

create table "BRAIN".LEVELS
(
	LEVELS_ID VARCHAR(64) not null primary key,
	CODE int not null,
	INTITULE VARCHAR(64),
	STATE_DB VARCHAR(64) default 'Business_Status_StateDb_Create',
	DT_CREATED DATE,
	DT_MODIFIED TIMESTAMP,
	USER_CREATED VARCHAR(64),
	USER_MODIFIED VARCHAR(64)
);

drop table "BRAIN".USERS;

create table "BRAIN".USERS
(
	USERS_ID VARCHAR(64) not null primary key,
	USER_CODE VARCHAR(64) not null,
	LOGIN VARCHAR(100) not null,
	PASSWORD CHAR(32) not null,
	LEVELS_ID VARCHAR(64) not null,
	FIRST_NAME VARCHAR(100),
	LAST_NAME VARCHAR(100),
	TEL VARCHAR(20),
	EMAIL VARCHAR(200),
	CNI VARCHAR(45),
	STATE_DB VARCHAR(64) default 'Business_Status_StateDb_Create',
	DT_CREATED DATE,
	DT_MODIFIED TIMESTAMP,
	USER_CREATED VARCHAR(64),
	USER_MODIFIED VARCHAR(64)
);

INSERT INTO "BRAIN".USERS (USERS_ID, USER_CODE, LOGIN, PASSWORD, LEVELS_ID, FIRST_NAME, LAST_NAME, TEL, EMAIL, CNI, STATE_DB, DT_CREATED, DT_MODIFIED, USER_CREATED, USER_MODIFIED) 
	VALUES ('1', '1', 'sa', '21232f297a57a5a743894a0e4a801fc3', 'CB-1', 'admin', 'admin', NULL, NULL, NULL, DEFAULT, CURRENT_DATE, CURRENT_TIMESTAMP, '1', '1');

	
INSERT INTO "BRAIN".LEVELS (LEVELS_ID, CODE, INTITULE, STATE_DB, DT_CREATED, DT_MODIFIED, USER_CREATED, USER_MODIFIED) 
	VALUES ('CB-1', 1, 'Business_Level_Administrateur', DEFAULT, CURRENT_DATE, CURRENT_TIMESTAMP, '1', '1')
	, ('CB-2', 2, 'Business_Level_Utilisateur', DEFAULT, CURRENT_DATE, CURRENT_TIMESTAMP, '1', '1')
	, ('CB-3', 3, 'Business_Level_Defaut', DEFAULT, CURRENT_DATE, CURRENT_TIMESTAMP, '1', '1');

Alter Table brain.users Add FOREIGN KEY (user_created) REFERENCES users(users_id);
Alter Table brain.users Add FOREIGN KEY (user_modified) REFERENCES users(users_id);
Alter Table brain.users Add FOREIGN KEY (levels_id) REFERENCES levels(levels_id);

Alter Table brain.levels Add FOREIGN KEY (user_created) REFERENCES users(users_id);
Alter Table brain.levels Add FOREIGN KEY (user_modified) REFERENCES users(users_id);


drop table "BRAIN".WIDGET;

create table "BRAIN".WIDGET
(
	WIDGET_ID VARCHAR(64) not null primary key,
	NAME VARCHAR(64) not null,
	PARENT VARCHAR(64) not null,
	LABEL VARCHAR(64) not null,
	LEVELS_ID VARCHAR(64) not null,
	AVAILABLE SMALLINT,
	DT_CREATED DATE,
	DT_MODIFIED TIMESTAMP,
	USER_CREATED VARCHAR(64),
	USER_MODIFIED VARCHAR(64)
);

Alter Table brain.widget Add FOREIGN KEY (parent) REFERENCES widget(widget_id);
Alter Table brain.widget Add FOREIGN KEY (levels_id) REFERENCES levels(levels_id);
Alter Table brain.widget Add FOREIGN KEY (user_created) REFERENCES users(users_id);
Alter Table brain.widget Add FOREIGN KEY (user_modified) REFERENCES users(users_id);

INSERT INTO "BRAIN".WIDGET (WIDGET_ID, NAME, PARENT, LABEL, LEVELS_ID, AVAILABLE, DT_CREATED, DT_MODIFIED, USER_CREATED, USER_MODIFIED)
	VALUES ('1', 'btnAdministration', '1', 'Business_Libelle_Administration', 'CB-1', 1, CURRENT_DATE, CURRENT_TIMESTAMP, '1', '1');

	
drop table "BRAIN".ETABLISSEMENT;
	
create table "BRAIN".ETABLISSEMENT
(
	ETABLISSEMENT_ID VARCHAR(64) not null primary key,
	NAME_ABREGE VARCHAR(64) not null,
	FULL_NAME VARCHAR(255) not null,
	ADRESSE VARCHAR(255) not null,
	VILLE VARCHAR(255) not null,
	TELEPHONE VARCHAR(16) not null,
	EMAIL VARCHAR(64) not null,
	STATE_DB VARCHAR(64) default 'Business_Status_StateDb_Create',
	DT_CREATED DATE,
	DT_MODIFIED TIMESTAMP,
	USER_CREATED VARCHAR(64),
	USER_MODIFIED VARCHAR(64)
);

Alter Table brain.etablissement Add FOREIGN KEY (user_created) REFERENCES users(users_id);
Alter Table brain.etablissement Add FOREIGN KEY (user_modified) REFERENCES users(users_id);

drop table "BRAIN".CATEGORIE;
	
create table "BRAIN".CATEGORIE
(
	CATEGORIE_ID VARCHAR(64) not null primary key,
	CODE VARCHAR(64) not null,
	INTITULE VARCHAR(64) not null,
	ETABLISSEMENT_ID VARCHAR(64) not null,
	STATE_DB VARCHAR(64) default 'Business_Status_StateDb_Create',
	DT_CREATED DATE,
	DT_MODIFIED TIMESTAMP,
	USER_CREATED VARCHAR(64),
	USER_MODIFIED VARCHAR(64)
);

Alter Table brain.categorie Add FOREIGN KEY (etablissement_id) REFERENCES etablissement(etablissement_id);
Alter Table brain.categorie Add FOREIGN KEY (user_created) REFERENCES users(users_id);
Alter Table brain.categorie Add FOREIGN KEY (user_modified) REFERENCES users(users_id);

drop table "BRAIN".CLASSE;
	
create table "BRAIN".CLASSE
(
	CLASSE_ID VARCHAR(64) not null primary key,
	CODE VARCHAR(64) not null,
	INTITULE VARCHAR(64) not null,
	CATEGORIE_ID VARCHAR(64) not null,
	GROUPE_ID VARCHAR(64),
	STATE_DB VARCHAR(64) default 'Business_Status_StateDb_Create',
	DT_CREATED DATE,
	DT_MODIFIED TIMESTAMP,
	USER_CREATED VARCHAR(64),
	USER_MODIFIED VARCHAR(64)
);

Alter Table brain.classe Add FOREIGN KEY (categorie_id) REFERENCES categorie(categorie_id);
Alter Table brain.classe Add FOREIGN KEY (user_created) REFERENCES users(users_id);
Alter Table brain.classe Add FOREIGN KEY (user_modified) REFERENCES users(users_id);

drop table "BRAIN".SALLE;
	
create table "BRAIN".SALLE
(
	SALLE_ID VARCHAR(64) not null primary key,
	CODE VARCHAR(64) not null,
	INTITULE VARCHAR(64) not null,
	CLASSE_ID VARCHAR(64) not null,
	CATEGORIE_ID VARCHAR(64) not null,
	STATE_DB VARCHAR(64) default 'Business_Status_StateDb_Create',
	DT_CREATED DATE,
	DT_MODIFIED TIMESTAMP,
	USER_CREATED VARCHAR(64),
	USER_MODIFIED VARCHAR(64)
);

Alter Table brain.salle Add FOREIGN KEY (categorie_id) REFERENCES categorie(categorie_id);
Alter Table brain.salle Add FOREIGN KEY (classe_id) REFERENCES classe(classe_id);
Alter Table brain.salle Add FOREIGN KEY (user_created) REFERENCES users(users_id);
Alter Table brain.salle Add FOREIGN KEY (user_modified) REFERENCES users(users_id);

drop table "BRAIN".GROUPE;
	
create table "BRAIN".GROUPE
(
	GROUPE_ID VARCHAR(64) not null primary key,
	CODE VARCHAR(64) not null,
	INTITULE VARCHAR(64) not null,
	SALLE_ID VARCHAR(64) not null,
	STATE_DB VARCHAR(64) default 'Business_Status_StateDb_Create',
	DT_CREATED DATE,
	DT_MODIFIED TIMESTAMP,
	USER_CREATED VARCHAR(64),
	USER_MODIFIED VARCHAR(64)
);

Alter Table brain.groupe Add FOREIGN KEY (salle_id) REFERENCES salle(salle_id);
Alter Table brain.groupe Add FOREIGN KEY (user_created) REFERENCES users(users_id);
Alter Table brain.groupe Add FOREIGN KEY (user_modified) REFERENCES users(users_id);
Alter Table brain.classe Add FOREIGN KEY (groupe_id) REFERENCES groupe(groupe_id);

drop table "BRAIN".STUDENT;
	
create table "BRAIN".STUDENT
(
	STUDENT_ID VARCHAR(64) not null primary key,
	CODE VARCHAR(64) not null,
	MATRICULE VARCHAR(64) not null,
	FIRST_NAME VARCHAR(64) not null,
	LAST_NAME VARCHAR(64) not null,
	BIRTHDAY DATE not null,
	BORN_LOCATION VARCHAR(64) not null,
	SEXE VARCHAR(64) not null,
	PROFIL BLOB,
	SALLE_ID VARCHAR(64) not null,
	CLASSE_ID VARCHAR(64) not null,
	DT_CREATED DATE,
	DT_MODIFIED TIMESTAMP,
	USER_CREATED VARCHAR(64),
	USER_MODIFIED VARCHAR(64)
);

Alter Table brain.student Add FOREIGN KEY (classe_id) REFERENCES classe(classe_id);
Alter Table brain.student Add FOREIGN KEY (salle_id) REFERENCES salle(salle_id);
Alter Table brain.student Add FOREIGN KEY (user_created) REFERENCES users(users_id);
Alter Table brain.student Add FOREIGN KEY (user_modified) REFERENCES users(users_id);


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
create table "BRAIN".MATIERE
(
	MATIERE_ID VARCHAR(64) not null primary key,
	NAME VARCHAR(64) not null,
	NAME_ABREGE VARCHAR(64) not null,
	LAST_NAME VARCHAR(64) not null,
	BIRTHDAY DATE not null,
	GROUPE SMALLINT,
	MATIERE_PARENT VARCHAR(64),
	BORN_LOCATION VARCHAR(64) not null,
	CLASSE VARCHAR(64) not null,
	CATEGORIE VARCHAR(64) not null,
	DT_CREATED DATE,
	DT_MODIFIED TIMESTAMP,
	USER_CREATED VARCHAR(64),
	USER_MODIFIED VARCHAR(64)
);


	
create table "BRAIN".ENSEIGNANT
(
	ENSEIGNANT_ID VARCHAR(64) not null primary key,
	NAME VARCHAR(64) not null,
	FIRST_NAME VARCHAR(64) not null,
	LAST_NAME VARCHAR(64) not null,
	BIRTHDAY DATE not null,
	BORN_LOCATION VARCHAR(64) not null,
	CNI VARCHAR(64) not null,
	DATE_EXPIRE DATE not null,
	MATIERE VARCHAR(64) not null,
	DT_CREATED DATE,
	DT_MODIFIED TIMESTAMP,
	USER_CREATED VARCHAR(64),
	USER_MODIFIED VARCHAR(64)
);




	
create table "BRAIN".COURS
(
	COURS_ID VARCHAR(64) not null primary key,
	ENSEIGNANT VARCHAR(64) not null,
	MATIERE VARCHAR(64) not null,
	SALLE VARCHAR(64) not null,
	CLASSE VARCHAR(64) not null,
	DT_CREATED DATE,
	DT_MODIFIED TIMESTAMP,
	USER_CREATED VARCHAR(64),
	USER_MODIFIED VARCHAR(64)
);


	
create table "BRAIN".ANNEE_ACADEMIC
(
	ANNEE_ACADEMIC_ID VARCHAR(64) not null primary key,
	DATE_OUVERTURE DATE not null,
	DATE_FERMETURE DATE,
	DT_CREATED DATE,
	DT_MODIFIED TIMESTAMP,
	USER_CREATED VARCHAR(64),
	USER_MODIFIED VARCHAR(64)
);



create table "BRAIN".TRIMESTRE
(
	TRIMESTRE_ID VARCHAR(64) not null primary key,
	DATE_OUVERTURE DATE not null,
	DATE_FERMETURE DATE,
	ANNEE_ACADEMIC VARCHAR(64) not null,
	DT_CREATED DATE,
	DT_MODIFIED TIMESTAMP,
	USER_CREATED VARCHAR(64),
	USER_MODIFIED VARCHAR(64)
);



create table "BRAIN".SEQUENCES
(
	SEQUENCES_ID VARCHAR(64) not null primary key,
	DATE_OUVERTURE DATE not null,
	DATE_FERMETURE DATE,
	TRIMESTRE_ID VARCHAR(64) not null,
	ANNEE_ACADEMIC VARCHAR(64) not null,
	DT_CREATED DATE,
	DT_MODIFIED TIMESTAMP,
	USER_CREATED VARCHAR(64),
	USER_MODIFIED VARCHAR(64)
);



create table "BRAIN".ALGORITHME
(
	ALGORITHME_ID VARCHAR(64) not null primary key,
	ENTITY VARCHAR(64) not null,
	ANNEE_ACADEMIC VARCHAR(64) not null,
	DT_CREATED DATE,
	DT_MODIFIED TIMESTAMP,
	USER_CREATED VARCHAR(64),
	USER_MODIFIED VARCHAR(64)
);

