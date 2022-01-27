DROP TABLE line_specification;
create table line_specification
(
    id                  SERIAL PRIMARY KEY,
    material_id         CHARACTER VARYING(100),
    name                CHARACTER VARYING(100),
    value               real,
    formula             INT,
    release_operation   CHARACTER VARYING(100),
    write_off_operation CHARACTER VARYING(100),
    specification       INT
);
