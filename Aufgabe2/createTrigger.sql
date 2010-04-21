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

CREATE TABLE logTabelle (
  id         INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
  username   VARCHAR(50),
  updateTime TIMESTAMP,
  actionDone VARCHAR(50)
);

CREATE TRIGGER logStammdatenU
AFTER UPDATE ON immobilie
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone) VALUES (USER,CURRENT TIMESTAMP,'UPDATE');

CREATE TRIGGER logStammdatenI
AFTER INSERT ON immobilie
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone) VALUES (USER,CURRENT TIMESTAMP,'INSERT');

CREATE TRIGGER logStammdatenD
AFTER DELETE ON immobilie
FOR EACH ROW
INSERT INTO logTabelle (username,updateTime,actionDone) VALUES (USER,CURRENT TIMESTAMP,'DELETE');

commit;
