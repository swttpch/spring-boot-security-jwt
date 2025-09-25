SET client_min_messages = WARNING;

DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

ALTER TABLE IF EXISTS token
   DROP CONSTRAINT IF EXISTS FKiblu4cjwvyntq3ugo31klp1c6;

DROP TABLE IF EXISTS token;
DROP TABLE IF EXISTS _user CASCADE;

DROP SEQUENCE IF EXISTS _user_seq;
DROP SEQUENCE IF EXISTS token_seq;

CREATE SEQUENCE _user_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE token_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE _user (
    id INT NOT NULL,
    email VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255) CHECK (role IN ('USER', 'ADMIN')),
    PRIMARY KEY (id)
);

CREATE TABLE token (
    expired BOOLEAN NOT NULL,
    id INT NOT NULL,
    revoked BOOLEAN NOT NULL,
    user_id INT,
    token VARCHAR(255) UNIQUE,
    token_type VARCHAR(255) CHECK (token_type IN ('BEARER')),
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS token
   ADD CONSTRAINT FKiblu4cjwvyntq3ugo31klp1c6
   FOREIGN KEY (user_id)
   REFERENCES _user (id);
