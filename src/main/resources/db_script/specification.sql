DROP TABLE specification;
create table specification
(
    id              SERIAL PRIMARY KEY     not null,
    name            CHARACTER VARYING(100),
    manufacturer_id CHARACTER VARYING(100) not null,
    doorType_id     INT
);
