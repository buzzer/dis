-- comment: aufgabe 2
-- call: db2 -svtf createTable.sql

CREATE TABLE aufg2_immobilie ( id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY
  (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY, wert DOUBLE NOT NULL );

commit;

-- quit
-- terminate
