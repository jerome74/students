DROP TABLE IF EXISTS Account;


CREATE TABLE Account( id INT AUTO_INCREMENT  PRIMARY KEY, username VARCHAR(250) NOT NULL, password VARCHAR(250) NOT NULL, active BOOLEAN NOT NULL );


INSERT INTO Account (username,password,active) VALUES ('wlongo', 'Ar3s01!!', true);
INSERT INTO Account (username,password,active) VALUES ('vvaccaro', 'pass01!!', true);
INSERT INTO Account (username,password,active) VALUES ('asabetta', 'pass02!!', true);
INSERT INTO Account (username,password,active) VALUES ('wlp.com', 'pass03!!', true);


commit;