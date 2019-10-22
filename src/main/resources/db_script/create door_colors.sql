DROP TABLE Door_Colors;
create table Door_Colors
(
  id                    SERIAL PRIMARY KEY,
  idManufacturerProgram CHARACTER VARYING(12)  NOT NULL,
  name                  CHARACTER VARYING(100) NOT NULL,
  picturePathFirst      CHARACTER VARYING(100),
  price                 int,
  smooth                int
);