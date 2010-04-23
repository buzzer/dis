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
--ON unternehmen
--ON privatperson
--GO

DROP TABLE logTabelle;
DROP TRIGGER logimmobilieU;
DROP TRIGGER logimmobilieI;
DROP TRIGGER logimmobilieD;
DROP TRIGGER logprivatpersonU;
DROP TRIGGER logprivatpersonI;
DROP TRIGGER logprivatpersonD;
DROP TRIGGER logunternehmenU;
DROP TRIGGER logunternehmenI;
DROP TRIGGER logunternehmenD;
DROP TRIGGER logvers_unU;
DROP TRIGGER logvers_unI;
DROP TRIGGER logvers_unD;
DROP TRIGGER logbankU;
DROP TRIGGER logbankI;
DROP TRIGGER logbankD;
DROP TRIGGER logversichertU;
DROP TRIGGER logversichertI;
DROP TRIGGER logversichertD;
DROP TRIGGER logdarlehenU;
DROP TRIGGER logdarlehenI;
DROP TRIGGER logdarlehenD;

CREATE TABLE logTabelle (
  id         INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
  username   VARCHAR(50),
  updateTime TIMESTAMP,
  actionDone VARCHAR(50),
  tableName  VARCHAR(50),
  tableID    INTEGER
);

-- Immobilie
CREATE TRIGGER logimmobilieU
AFTER UPDATE ON immobilie
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'UPDATE','IMMOBILIE',N_ROW.ImmoID);

CREATE TRIGGER logimmobilieI
AFTER INSERT ON immobilie
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'INSERT','IMMOBILIE',N_ROW.ImmoID);

CREATE TRIGGER logimmobilieD
AFTER DELETE ON immobilie
REFERENCING OLD AS O_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'DELETE','IMMOBILIE',O_ROW.ImmoID);

-- Privatperson
CREATE TRIGGER logprivatpersonU
AFTER UPDATE ON privatperson
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'UPDATE','PRIVATPERSON',N_ROW.PersID);

CREATE TRIGGER logprivatpersonI
AFTER INSERT ON privatperson
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'INSERT','PRIVATPERSON',N_ROW.PersID);

CREATE TRIGGER logprivatpersonD
AFTER DELETE ON privatperson
REFERENCING OLD AS O_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'DELETE','PRIVATPERSON',O_ROW.PersID);

-- Unternehmen
CREATE TRIGGER logunternehmenU
AFTER UPDATE ON unternehmen
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'UPDATE','UNTERNEHMEN',N_ROW.UNid);

CREATE TRIGGER logunternehmenI
AFTER INSERT ON unternehmen
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'INSERT','UNTERNEHMEN',N_ROW.UNid);

CREATE TRIGGER logunternehmenD
AFTER DELETE ON unternehmen
REFERENCING OLD AS O_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'DELETE','UNTERNEHMEN',O_ROW.UNid);

--VersicherungsUN
CREATE TRIGGER logvers_unU
AFTER UPDATE ON vers_un
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'UPDATE','VERS_UN',N_ROW.VersUNid);

CREATE TRIGGER logvers_unI
AFTER INSERT ON vers_un
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'INSERT','VERS_UN',N_ROW.VersUNid);

CREATE TRIGGER logvers_unD
AFTER DELETE ON vers_un
REFERENCING OLD AS O_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'DELETE','VERS_UN',O_ROW.VersUNid);

-- Bank
CREATE TRIGGER logbankU
AFTER UPDATE ON bank
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'UPDATE','BANK',N_ROW.BankID);

CREATE TRIGGER logbankI
AFTER INSERT ON bank
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'INSERT','BANK',N_ROW.BankID);

CREATE TRIGGER logbankD
AFTER DELETE ON bank
REFERENCING OLD AS O_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'DELETE','BANK',O_ROW.BankID);

-- Versichert
CREATE TRIGGER logversichertU
AFTER UPDATE ON versichert
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'UPDATE','VERSICHERT',N_ROW.LebVersID);

CREATE TRIGGER logversichertI
AFTER INSERT ON versichert
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'INSERT','VERSICHERT',N_ROW.LebVersID);

CREATE TRIGGER logversichertD
AFTER DELETE ON versichert
REFERENCING OLD AS O_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'DELETE','VERSICHERT',O_ROW.LebVersID);

-- Darlehen
CREATE TRIGGER logdarlehenU
AFTER UPDATE ON darlehen
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'UPDATE','DARLEHEN',N_ROW.DarlID);

CREATE TRIGGER logdarlehenI
AFTER INSERT ON darlehen
REFERENCING NEW AS N_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'INSERT','DARLEHEN',N_ROW.DarlID);

CREATE TRIGGER logdarlehenD
AFTER DELETE ON darlehen
REFERENCING OLD AS O_ROW
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone,tableName,tableID) VALUES (USER,CURRENT TIMESTAMP,'DELETE','DARLEHEN',O_ROW.DarlID);

commit;
