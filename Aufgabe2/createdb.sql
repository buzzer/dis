-- comment: aufgabe 2
-- call: db2 -svtf createTable.sql

-- DDL: Create database
-- Remarks: Jede Immobilie hat ein Darlehensnehmer
--          Bank gibt Bank kein Darlehen
--          Generalisierung: Hausklassenmodell
--          Geldbetraege <= 10 Mrd. Einheiten

DROP TABLE darlehen;
DROP TABLE immobilie;
DROP TABLE privatperson;
DROP TABLE unternehmen;
DROP TABLE vers_un;
DROP TABLE bank;
DROP TABLE versichert;

CREATE TABLE darlehen(
  DarlID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
  BankID INTEGER NOT NULL,
  PersID INTEGER, -- UNIQUE requires not null
  UNid INTEGER,
  VersUNid INTEGER,
  ImmoID INTEGER,
  LebVersID INTEGER,
  Betrag NUMERIC(12,2) NOT NULL,
  Zinssatz NUMERIC(5,2) NOT NULL,
  Tilgungsrate NUMERIC(5,2),
  Restschuld NUMERIC(12,2),
  CONSTRAINT NurEinDarlNehmer CHECK (
    (PersID>0 AND UNid=0 AND VersUNid=0) OR
    (PersID=0 AND UNid>0 AND VersUNid=0) OR
    (PersID=0 AND UNid=0 AND VersUNid>0)
  )
);

CREATE TABLE immobilie(
  ImmoID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
  PersID INTEGER,
  UNid INTEGER,
  VersUNid INTEGER,
  BankID INTEGER,
  Wert NUMERIC(12,2) NOT NULL,
  CONSTRAINT NurEinBesitzer CHECK (
    (PersID>0 AND UNid=0 AND VersUNid=0 AND BankID=0) OR
    (PersID=0 AND UNid>0 AND VersUNid=0 AND BankID=0) OR
    (PersID=0 AND UNid=0 AND VersUNid>0 AND BankID=0) OR
    (PersID=0 AND UNid=0 AND VersUNid=0 AND BankID>0)
  )
);

CREATE TABLE privatperson(
  PersID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
  Name VARCHAR(50) NOT NULL,
  Str VARCHAR(50) NOT NULL,
  PLZ NUMERIC (5,0) NOT NULL,
  Ort VARCHAR(50) NOT NULL,
  Vorname VARCHAR(50) NOT NULL
);

CREATE TABLE unternehmen(
  UNid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
  Name VARCHAR(50) NOT NULL,
  Str VARCHAR(50) NOT NULL,
  PLZ NUMERIC (5,0) NOT NULL,
  Ort VARCHAR(50) NOT NULL,
  Rechtsform VARCHAR(50) NOT NULL,
  Eigenkapital NUMERIC(12,2)
);

CREATE TABLE vers_un(
  VersUNid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
  Name VARCHAR(50) NOT NULL,
  Str VARCHAR(50) NOT NULL,
  PLZ NUMERIC(5,0) NOT NULL,
  Ort VARCHAR(50) NOT NULL,
  Rechtsform VARCHAR(50) NOT NULL,
  Eigenkapital NUMERIC(12,2)
);

CREATE TABLE bank(
  BankID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
  Name VARCHAR(50) NOT NULL,
  Str VARCHAR(50) NOT NULL,
  PLZ NUMERIC(5,0) NOT NULL,
  Ort VARCHAR(50) NOT NULL,
  Rechtsform VARCHAR(50) NOT NULL,
  Eigenkapital NUMERIC(12,2)
);

CREATE TABLE versichert(
  LebVersID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
  PersID INTEGER NOT NULL,
  VersUNid INTEGER NOT NULL,
  Betrag NUMERIC(12,2) NOT NULL
);

ALTER TABLE darlehen ADD FOREIGN KEY (BankID) REFERENCES bank(BankID) ON DELETE RESTRICT;
ALTER TABLE darlehen ADD FOREIGN KEY (PersID) REFERENCES privatperson(PersID) ON DELETE RESTRICT;
ALTER TABLE darlehen ADD FOREIGN KEY (UNid) REFERENCES unternehmen(UNid) ON DELETE RESTRICT;
ALTER TABLE darlehen ADD FOREIGN KEY (VersUNid) REFERENCES vers_un(VersUNid) ON DELETE RESTRICT;
ALTER TABLE darlehen ADD FOREIGN KEY (ImmoID) REFERENCES immobilie(ImmoID) ON DELETE RESTRICT;
ALTER TABLE darlehen ADD FOREIGN KEY (LebVersID) REFERENCES versichert(LebVersID) ON DELETE RESTRICT;
--ALTER TABLE darlehen ADD CONSTRAINT NurEinKredit UNIQUE (BankID, PersID);
--ALTER TABLE darlehen ADD CONSTRAINT Sicherheiten CHECK (
  --(Betrag<=10000) OR
  --((Betrag>10000) AND (ImmoID IS NOT NULL)) OR
  --((Betrag>100000) AND (ImmoID IS NOT NULL) AND (LebVersID IS NOT NULL))
--);

ALTER TABLE immobilie ADD FOREIGN KEY (PersID) REFERENCES privatperson(PersID) ON DELETE RESTRICT;
ALTER TABLE immobilie ADD FOREIGN KEY (UNid) REFERENCES unternehmen(UNid) ON DELETE RESTRICT;
ALTER TABLE immobilie ADD FOREIGN KEY (VersUNid) REFERENCES vers_un(VersUNid) ON DELETE RESTRICT;
ALTER TABLE immobilie ADD FOREIGN KEY (BankID) REFERENCES bank(BankID) ON DELETE RESTRICT;
ALTER TABLE versichert ADD FOREIGN KEY (PersID) REFERENCES privatperson(PersID) ON DELETE RESTRICT;
ALTER TABLE versichert ADD FOREIGN KEY (VersUNid) REFERENCES vers_un(VersUNid) ON DELETE RESTRICT;

COMMIT;

-- quit
-- terminate
