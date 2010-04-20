-- Versicherungen
INSERT INTO vers_un (Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES ('Versicherung1','Str1',20001,'Ort1','Rechtsform1',100001);
INSERT INTO vers_un (Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES ('Versicherung2','Str2',20002,'Ort2','Rechtsform2',100002);
INSERT INTO vers_un (Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES ('Versicherung3','Str3',20003,'Ort3','Rechtsform3',100003);
INSERT INTO vers_un (Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES ('Versicherung4','Str4',20004,'Ort4','Rechtsform4',100004);
INSERT INTO vers_un (Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES ('Versicherung5','Str5',20005,'Ort5','Rechtsform5',100005);
-- Stammdaten
-- Banken
INSERT INTO bank (Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES ('Bank1','Str6',20006,'Ort6','Rechtsform1',200001);
INSERT INTO bank (Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES ('Bank2','Str7',20007,'Ort7','Rechtsform2',200002);
INSERT INTO bank (Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES ('Bank3','Str8',20008,'Ort8','Rechtsform3',200003);
INSERT INTO bank (Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES ('Bank4','Str9',20009,'Ort9','Rechtsform4',200004);
INSERT INTO bank (Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES ('Bank5','Str10',20010,'Ort10','Rechtsform5',200005);
-- Unternehmen
INSERT INTO unternehmen (Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES ('Unternehmen1','Str6',30006,'Ort6','Rechtsform1',20001);
INSERT INTO unternehmen (Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES ('Unternehmen2','Str7',30007,'Ort7','Rechtsform2',20002);
INSERT INTO unternehmen (Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES ('Unternehmen3','Str8',30008,'Ort8','Rechtsform3',20003);
INSERT INTO unternehmen (Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES ('Unternehmen4','Str9',30009,'Ort9','Rechtsform4',20004);
INSERT INTO unternehmen (Name,Str,PLZ,Ort,Rechtsform,Eigenkapital) VALUES ('Unternehmen5','Str10',30010,'Ort10','Rechtsform5',20005);
-- Privatpersonen
INSERT INTO privatperson (Name,Str,PLZ,Ort,Vorname) VALUES ('Name1','Str6',20006,'Ort6','Vorname1');
INSERT INTO privatperson (Name,Str,PLZ,Ort,Vorname) VALUES ('Name2','Str7',20007,'Ort7','Vorname2');
INSERT INTO privatperson (Name,Str,PLZ,Ort,Vorname) VALUES ('Name3','Str8',20008,'Ort8','Vorname3');
INSERT INTO privatperson (Name,Str,PLZ,Ort,Vorname) VALUES ('Name4','Str9',20009,'Ort9','Vorname4');
INSERT INTO privatperson (Name,Str,PLZ,Ort,Vorname) VALUES ('Name5','Str10',20010,'Ort10','Vorname5');

-- Immobilien
INSERT INTO immobilie (PersID,UNid,VersUNid,BankID,Wert) VALUES (    1,NULL,NULL,NULL, 50000);
INSERT INTO immobilie (PersID,UNid,VersUNid,BankID,Wert) VALUES ( NULL,   1,NULL,NULL, 60000);
INSERT INTO immobilie (PersID,UNid,VersUNid,BankID,Wert) VALUES ( NULL,NULL,   1,NULL, 70000);
INSERT INTO immobilie (PersID,UNid,VersUNid,BankID,Wert) VALUES ( NULL,NULL,NULL,   1, 80000);
INSERT INTO immobilie (PersID,UNid,VersUNid,BankID,Wert) VALUES (    2,NULL,NULL,NULL, 90000);
-- Darlehen
INSERT INTO darlehen (PersID,UNid,VersUNid,BankID,Betrag,ImmoID,Zinssatz,Tilgungsrate,Restschuld) VALUES (NULL,NULL,   5,1,5000,   1,5.05,20,3000);
INSERT INTO darlehen (PersID,UNid,VersUNid,BankID,Betrag,ImmoID,Zinssatz,Tilgungsrate,Restschuld) VALUES (NULL,   5,NULL,2,6000,NULL,5.06,21,3000);
INSERT INTO darlehen (PersID,UNid,VersUNid,BankID,Betrag,ImmoID,Zinssatz,Tilgungsrate,Restschuld) VALUES (   5,NULL,NULL,2,7000,NULL,5.07,22,1000);
INSERT INTO darlehen (PersID,UNid,VersUNid,BankID,Betrag,ImmoID,Zinssatz,Tilgungsrate,Restschuld) VALUES (NULL,NULL,NULL,3,8000,   3,5.10,23,4000);
INSERT INTO darlehen (PersID,UNid,VersUNid,BankID,Betrag,ImmoID,Zinssatz,Tilgungsrate,Restschuld) VALUES (NULL,NULL,NULL,5,9000,NULL,5.04,24,3000);
-- Versicherungen
INSERT INTO versichert (PersID,VersUNid,Betrag) VALUES (1,1,20006);
INSERT INTO versichert (PersID,VersUNid,Betrag) VALUES (2,2,20007);
INSERT INTO versichert (PersID,VersUNid,Betrag) VALUES (3,2,20008);
INSERT INTO versichert (PersID,VersUNid,Betrag) VALUES (4,4,20009);
INSERT INTO versichert (PersID,VersUNid,Betrag) VALUES (5,5,20010);

COMMIT;
