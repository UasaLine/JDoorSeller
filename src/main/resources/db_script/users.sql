DROP TABLE users;
CREATE TABLE users
(
  id       SERIAL PRIMARY KEY,
  login    CHARACTER VARYING(100),
  password CHARACTER VARYING(100),
  discount int
);