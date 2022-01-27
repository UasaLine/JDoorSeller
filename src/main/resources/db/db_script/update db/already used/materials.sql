DROP TABLE materials;
create table materials
(
    id                      SERIAL PRIMARY KEY,
    id_manufacturer_program CHARACTER VARYING(12),
    name                    CHARACTER VARYING(100),
    components              int,
    components_parent_id    int,
    price                   int
);
