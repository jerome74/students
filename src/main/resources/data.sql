DROP TABLE IF EXISTS Student;

CREATE TABLE Student ( id INT AUTO_INCREMENT  PRIMARY KEY, name VARCHAR(250) NOT NULL);

INSERT INTO Student (name) VALUES ('Alessandro SABETTA');
INSERT INTO Student (name) VALUES ('Valentina VACCARO');
INSERT INTO Student (name) VALUES ('Nicola GRAZIANO');

commit;