-- comment: aufgabe 7
-- call: db2 -svtf createTable.sql

-- DDL: Create database
-- Remarks: 
-- Sternschema for data warehouse

DROP TABLE aufg7_faktentabelle;
DROP TABLE aufg7_shop;
DROP TABLE aufg7_artikel;
DROP TABLE aufg7_zeit;
-- Create also CSV table
--DROP TABLE aufg7_csvtabelle;

CREATE TABLE aufg7_faktentabelle(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
  shopId INTEGER,
  artikelId INTEGER,
  zeitId DATE,
  verkauft INTEGER,
  umsatz NUMERIC(12,2)
);

CREATE TABLE aufg7_shop(
  id INTEGER NOT NULL PRIMARY KEY,
  name VARCHAR(50),
  land VARCHAR(50),
  region VARCHAR(50),
  stadt VARCHAR(50)
);

CREATE TABLE aufg7_artikel(
  id INTEGER NOT NULL PRIMARY KEY,
  name VARCHAR(50),
  preis NUMERIC(12,2),
  produktKat VARCHAR(50),
  produktFam VARCHAR(50),
  ProduktGru VARCHAR(50)
);

CREATE TABLE aufg7_zeit(
  --id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
  datum DATE NOT NULL PRIMARY KEY,
  quartal INTEGER,
  monat INTEGER,
  jahr INTEGER
);

--CREATE TABLE aufg7_csvtabelle(
	--id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
	--datum DATE,
	--shop VARCHAR(50),
	--artikel VARCHAR(50),
	--verkauft INTEGER,
	--umsatz NUMERIC (12,2)
--);

ALTER TABLE aufg7_faktentabelle ADD FOREIGN KEY (shopId) REFERENCES aufg7_shop(id) ON DELETE RESTRICT;
ALTER TABLE aufg7_faktentabelle ADD FOREIGN KEY (artikelId) REFERENCES aufg7_artikel(id) ON DELETE RESTRICT;
ALTER TABLE aufg7_faktentabelle ADD FOREIGN KEY (zeitId) REFERENCES aufg7_zeit(datum) ON DELETE RESTRICT;

COMMIT;

-- quit
-- terminate
