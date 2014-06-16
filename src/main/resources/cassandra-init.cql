CREATE KEYSPACE healthtrust WITH replication={'class' : 'SimpleStrategy', 'replication_factor':1};
USE healthtrust;

CREATE TABLE patient(
    id text,
    fname text,
    lname text,
    position text,
    PRIMARY KEY(id));

INSERT INTO patient(id, fname, lname, position) values('bobby.brown@healthtrustpg.com', 'Bobby', 'Brown', 'Developer');

INSERT INTO patient(id, fname, lname, position) values('michael.arnold@healthtrustpg.com', 'Michael', 'Arnold', 'Manager');

INSERT INTO patient(id, fname, lname, position) values('Kristin.C@healthtrustpg.com', 'Kristin', 'C', 'Developer');

INSERT INTO patient(id, fname, lname, position) values('Jerry.R@healthtrustpg.com', 'Jerry', 'R', 'Business Analyst');

INSERT INTO patient(id, fname, lname, position) values('Ryan.Bevens@healthtrustpg.com', 'Ryan', 'Bevens', 'SR Quality Assurance Analyst');

INSERT INTO patient(id, fname, lname, position) values('Syed.N@healthtrustpg.com', 'Syed', 'N', 'Software Architect');