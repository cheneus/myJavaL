DROP TABLE people IF EXISTS;
DROP TABLE house IF EXISTS;

CREATE TABLE people (
    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);

CREATE TABLE house (
    house_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    income   DOUBLE,
    pet      VARCHAR(20)
);