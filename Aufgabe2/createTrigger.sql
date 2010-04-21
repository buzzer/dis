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
DISABLE TRIGGER ChangeTrigger
ON unternehmen
ON privatperson
GO


CREATE TRIGGER ChangeTrigger
ON unternehmen
ON privatperson
AFTER INSERT
AFTER UPDATE
AFTER DELETE
SELECT ProtID FROM INSERT
SELECT ProtID FROM UPDATE
SELECT ProtID FROM DELETE
GO
