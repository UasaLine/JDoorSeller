DROP TABLE Metal;
create table Metal
(
  id SERIAL PRIMARY KEY,
  id_manufacturer_program CHARACTER VARYING(12) NOT NULL,
  name  CHARACTER VARYING(100) NOT NULL,
  name_displayed CHARACTER VARYING(100),
  index_heft INT,
  is_used CHARACTER VARYING(100) NOT NULL,
  price INT

);