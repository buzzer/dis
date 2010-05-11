/*
Erklärung
---------
 
CREATE TRIGGER SPION1
ON dbo.DatabaseLog
 - Ein neuer Trigger "Spion1"
   wird an die Tabelle DatabaseLog angefügt
 
AFTER DELETE             
 - Der Trigger wird nach der DELETE Anweisung ausgeführt. 
 - Wenn die Anweisung vor der Beendigung abbricht,
   wird der Trigger nicht ausgeführt. 
 
SELECT DatabaseLogID FROM DELETED
 - Dient zum Empfangen von Werten aus der DELETE-Anweisung.
 - Für UPDATE-Anweisungen nimmt man UPDATED und INSERTED für INSERT-Anweisungen
 
*/
--DISABLE TRIGGER ChangeTrigger
--ON Aufg3_unternehmen
--ON Aufg3_privatperson
--GO

--DROP TABLE logTabelle;
DROP TRIGGER logAufg3_immobilieU;
DROP TRIGGER logAufg3_immobilieI;
DROP TRIGGER logAufg3_immobilieD;
DROP TRIGGER logAufg3_darlehensnehmerU;
DROP TRIGGER logAufg3_darlehensnehmerI;
DROP TRIGGER logAufg3_darlehensnehmerD;
DROP TRIGGER logAufg3_versichertU;
DROP TRIGGER logAufg3_versichertI;
DROP TRIGGER logAufg3_versichertD;
DROP TRIGGER logAufg3_darlehenU;
DROP TRIGGER logAufg3_darlehenI;
DROP TRIGGER logAufg3_darlehenD;
--DROP TRIGGER logAufg3_privatpersonU;
--DROP TRIGGER logAufg3_privatpersonI;
--DROP TRIGGER logAufg3_privatpersonD;
--DROP TRIGGER logAufg3_unternehmenU;
--DROP TRIGGER logAufg3_unternehmenI;
--DROP TRIGGER logAufg3_unternehmenD;
--DROP TRIGGER logAufg3_vers_unU;
--DROP TRIGGER logAufg3_vers_unI;
--DROP TRIGGER logAufg3_vers_unD;
--DROP TRIGGER logAufg3_bankU;
--DROP TRIGGER logAufg3_bankI;
--DROP TRIGGER logAufg3_bankD;

--CREATE TABLE logTabelle (
  --id         INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
  --username   VARCHAR(50),
  --updateTime TIMESTAMP,
  --actionDone VARCHAR(50),
  --tableName  VARCHAR(50),
  --tableID    INTEGER
--);

-- Aufg3_immobilie
CREATE TRIGGER logAufg3_immobilieU
AFTER UPDATE ON Aufg3_immobilie
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'UPDATE','Aufg3_immobilie',N_ROW.ImmoID);

CREATE TRIGGER logAufg3_immobilieI
AFTER INSERT ON Aufg3_immobilie
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'INSERT','Aufg3_immobilie',N_ROW.ImmoID);

CREATE TRIGGER logAufg3_immobilieD
AFTER DELETE ON Aufg3_immobilie
REFERENCING OLD AS O_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'DELETE','Aufg3_immobilie',O_ROW.ImmoID);

-- Aufg3_darlehensnehmer
CREATE TRIGGER logAufg3_darlehensnehmerU
AFTER UPDATE ON Aufg3_darlehensnehmer
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'UPDATE','Aufg3_darlehensnehmer',N_ROW.DNID);

CREATE TRIGGER logAufg3_darlehensnehmerI
AFTER INSERT ON Aufg3_darlehensnehmer
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'INSERT','Aufg3_darlehensnehmer',N_ROW.DNID);

CREATE TRIGGER logAufg3_darlehensnehmerD
AFTER DELETE ON Aufg3_darlehensnehmer
REFERENCING OLD AS O_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'DELETE','Aufg3_darlehensnehmer',O_ROW.DNID);

-- Aufg3_versichert
CREATE TRIGGER logAufg3_versichertU
AFTER UPDATE ON Aufg3_versichert
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'UPDATE','Aufg3_versichert',N_ROW.LebVersID);

CREATE TRIGGER logAufg3_versichertI
AFTER INSERT ON Aufg3_versichert
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'INSERT','Aufg3_versichert',N_ROW.LebVersID);

CREATE TRIGGER logAufg3_versichertD
AFTER DELETE ON Aufg3_versichert
REFERENCING OLD AS O_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'DELETE','Aufg3_versichert',O_ROW.LebVersID);

-- Aufg3_darlehen
CREATE TRIGGER logAufg3_darlehenU
AFTER UPDATE ON Aufg3_darlehen
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'UPDATE','Aufg3_darlehen',N_ROW.DarlID);

CREATE TRIGGER logAufg3_darlehenI
AFTER INSERT ON Aufg3_darlehen
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'INSERT','Aufg3_darlehen',N_ROW.DarlID);

CREATE TRIGGER logAufg3_darlehenD
AFTER DELETE ON Aufg3_darlehen
REFERENCING OLD AS O_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'DELETE','Aufg3_darlehen',O_ROW.DarlID);

---- Aufg3_privatperson
--CREATE TRIGGER logAufg3_privatpersonU
--AFTER UPDATE ON Aufg3_privatperson
--REFERENCING NEW AS N_ROW
--FOR EACH ROW
--INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'UPDATE','Aufg3_privatperson',N_ROW.DNID);

--CREATE TRIGGER logAufg3_privatpersonI
--AFTER INSERT ON Aufg3_privatperson
--REFERENCING NEW AS N_ROW
--FOR EACH ROW
--INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'INSERT','Aufg3_privatperson',N_ROW.DNID);

--CREATE TRIGGER logAufg3_privatpersonD
--AFTER DELETE ON Aufg3_privatperson
--REFERENCING OLD AS O_ROW
--FOR EACH ROW
--INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'DELETE','Aufg3_privatperson',O_ROW.DNID);

---- Aufg3_unternehmen
--CREATE TRIGGER logAufg3_unternehmenU
--AFTER UPDATE ON Aufg3_unternehmen
--REFERENCING NEW AS N_ROW
--FOR EACH ROW
--INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'UPDATE','Aufg3_unternehmen',N_ROW.DNID);

--CREATE TRIGGER logAufg3_unternehmenI
--AFTER INSERT ON Aufg3_unternehmen
--REFERENCING NEW AS N_ROW
--FOR EACH ROW
--INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'INSERT','Aufg3_unternehmen',N_ROW.DNID);

--CREATE TRIGGER logAufg3_unternehmenD
--AFTER DELETE ON Aufg3_unternehmen
--REFERENCING OLD AS O_ROW
--FOR EACH ROW
--INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'DELETE','Aufg3_unternehmen',O_ROW.DNID);

----VersicherungsUN
--CREATE TRIGGER logAufg3_vers_unU
--AFTER UPDATE ON Aufg3_vers_un
--REFERENCING NEW AS N_ROW
--FOR EACH ROW
--INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'UPDATE','Aufg3_vers_un',N_ROW.DNID);

--CREATE TRIGGER logAufg3_vers_unI
--AFTER INSERT ON Aufg3_vers_un
--REFERENCING NEW AS N_ROW
--FOR EACH ROW
--INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'INSERT','Aufg3_vers_un',N_ROW.DNID);

--CREATE TRIGGER logAufg3_vers_unD
--AFTER DELETE ON Aufg3_vers_un
--REFERENCING OLD AS O_ROW
--FOR EACH ROW
--INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'DELETE','Aufg3_vers_un',O_ROW.DNID);

---- Aufg3_bank
--CREATE TRIGGER logAufg3_bankU
--AFTER UPDATE ON Aufg3_bank
--REFERENCING NEW AS N_ROW
--FOR EACH ROW
--INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'UPDATE','Aufg3_bank',N_ROW.DNID);

--CREATE TRIGGER logAufg3_bankI
--AFTER INSERT ON Aufg3_bank
--REFERENCING NEW AS N_ROW
--FOR EACH ROW
--INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'INSERT','Aufg3_bank',N_ROW.DNID);

--CREATE TRIGGER logAufg3_bankD
--AFTER DELETE ON Aufg3_bank
--REFERENCING OLD AS O_ROW
--FOR EACH ROW
--INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'DELETE','Aufg3_bank',O_ROW.DNID);

commit;
