DROP TABLE peephole CASCADE;
CREATE TABLE peephole
(
    id              SERIAL PRIMARY KEY,
    manufacturer_id CHARACTER VARYING(20),
    position        CHARACTER VARYING(100),
    height          int
);
