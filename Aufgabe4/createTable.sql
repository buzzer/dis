-- comment: aufgabe 4
-- call: db2 -svtf createTable.sql
DROP TABLE OPK;
DROP TABLE MPK;

CREATE TABLE OPK (
  ID INTEGER,
  Name VARCHAR(50)
);
CREATE TABLE MPK (
  ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
  Name VARCHAR(50)
);

-- OPK samples
INSERT INTO OPK (ID,Name) VALUES (1,'Name1');
INSERT INTO OPK (ID,Name) VALUES (2,'Name2');
INSERT INTO OPK (ID,Name) VALUES (3,'Name3');
INSERT INTO OPK (ID,Name) VALUES (4,'Name4');
INSERT INTO OPK (ID,Name) VALUES (5,'Name5');
INSERT INTO OPK (ID,Name) VALUES (6,'Name6');
INSERT INTO OPK (ID,Name) VALUES (7,'Name7');
INSERT INTO OPK (ID,Name) VALUES (8,'Name8');
INSERT INTO OPK (ID,Name) VALUES (9,'Name9');
INSERT INTO OPK (ID,Name) VALUES (10,'Name10');
-- MPK samples
INSERT INTO MPK (Name) VALUES ('Name1');
INSERT INTO MPK (Name) VALUES ('Name2');
INSERT INTO MPK (Name) VALUES ('Name3');
INSERT INTO MPK (Name) VALUES ('Name4');
INSERT INTO MPK (Name) VALUES ('Name5');
INSERT INTO MPK (Name) VALUES ('Name6');
INSERT INTO MPK (Name) VALUES ('Name7');
INSERT INTO MPK (Name) VALUES ('Name8');
INSERT INTO MPK (Name) VALUES ('Name9');
INSERT INTO MPK (Name) VALUES ('Name10');

COMMIT;

-- SELECT * FROM OPK WHERE ID=1;
-- SELECT * FROM OPK WHERE ID>5;
-- INSERT INTO OPK (ID,Name) VALUES (20,'Name20');
-- COMMIT;
-- UPDATE OPK SET Name='Name1_a' WHERE ID=1;

-- quit
-- terminate

