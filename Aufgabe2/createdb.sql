-- comment: aufgabe 2
-- call: db2 -svtf createTable.sql

-- DDL: Create database
-- Remarks: Jede Immobilie hat ein Darlehensnehmer
--          Bank gibt Bank kein Darlehen
--          Generalisierung: Hausklassenmodell
--          Geldbeträge <= 10 Mrd. Einheiten

DROP darlehen
DROP immobilie
DROP privatperson
DROP unternehmen
DROP versun
DROP bank
DROP versichert

CREATE TABLE darlehen(
  DarlID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  BankID INTEGER NOT NULL FOREIGN KEY REFERENCES bank(BankID),
  PersID INTEGER FOREIGN KEY REFERENCES privatperson(PersID),
  UNid INTEGER FOREIGN KEY REFERENCES unternehmen(UNid),
  VersUNid INTEGER FOREIGN KEY REFERENCES vers_un(VERSUNid),
  ImmoID INTEGER FOREIGN KEY REFERENCES immobilie(ImmoID),
  Betrag NUMERIC(12,2) NOT NULL,
  Zinssatz NUMERIC(5,2) NOT NULL,
  Tilgungsrate NUMERIC(5,2),
  Restschuld NUMERIC(12,2)
);

CREATE TABLE immobilie(
  ImmoID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  PersID INTEGER FOREIGN KEY REFERENCES privatperson(PersID),
  UNid INTEGER FOREIGN KEY REFERENCES unternehmen(UNid),
  Wert NUMERIC(12,2) NOT NULL
);

CREATE TABLE privatperson(
  PersID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Name VARCHAR(50) NOT NULL,
  Str VARCHAR(50) NOT NULL,
  PLZ NUMERIC (5,0) NOT NULL,
  Ort VARCHAR(50) NOT NULL,
  Vorname VARCHAR(50) NOT NULL
);

CREATE TABLE unternehmen(
  UNid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Name VARCHAR(50) NOT NULL,
  Str VARCHAR(50) NOT NULL,
  PLZ NUMERIC (5,0) NOT NULL,
  Ort VARCHAR(50) NOT NULL,
  Rechtsform VARCHAR(50) NOT NULL,
  Eigenkapital NUMERIC(12,2)
);

CREATE TABLE vers_un(
  VersUNid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Name VARCHAR(50) NOT NULL,
  Str VARCHAR(50) NOT NULL,
  PLZ NUMERIC(5,0) NOT NULL,
  Ort VARCHAR(50) NOT NULL,
  Rechtsform VARCHAR(50) NOT NULL,
  Eigenkapital NUMERIC(12,2)
);

CREATE TABLE bank(
  BankID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Name VARCHAR(50) NOT NULL,
  Str VARCHAR(50) NOT NULL,
  PLZ NUMERIC(5,0) NOT NULL,
  Ort VARCHAR(50) NOT NULL,
  Rechtsform VARCHAR(50) NOT NULL,
  Eigenkapital NUMERIC(12,2)
);

CREATE TABLE versichert(
  LebVersID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  PersID INTEGER NOT NULL FOREIGN KEY REFERENCES privatpers(PersID),
  VersUNid INTEGER NOT NULL FOREIGN KEY REFERENCES vers_un(VersUNid)
  Betrag NUMERIC(12,2)
);

commit;

-- quit
-- terminate
