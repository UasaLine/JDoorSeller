
create table Limitation_Door
(
  id SERIAL PRIMARY KEY,
  doorClass_id INT NOT NULL,
  typeSettings CHARACTER VARYING(100),
  firstItem CHARACTER VARYING(100),
  secondItem CHARACTER VARYING(100),
  startRestriction INT,
  stopRestriction INT,
  comment CHARACTER VARYING(100),
    defaultValue INT NOT NULL
    );



create table Size_Door_Parts
(
  id SERIAL PRIMARY KEY,
  doorType_id INT,
  name  CHARACTER VARYING(100) NOT NULL,
  condition CHARACTER VARYING(100),
  width CHARACTER VARYING(100) NOT NULL,
  height CHARACTER VARYING(100) NOT NULL,
  quantity CHARACTER VARYING(100) NOT NULL
);




