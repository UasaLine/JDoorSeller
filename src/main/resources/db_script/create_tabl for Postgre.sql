

--DROP TABLE Door_Class;
CREATE TABLE Door_Class
(
  Id SERIAL PRIMARY KEY,
  name CHARACTER VARYING(100),
  description CHARACTER VARYING(100),
  fireproof CHARACTER VARYING(100),
  hot int
);

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



create table Metal
(
  id SERIAL PRIMARY KEY,
  id_manufacturer_program CHARACTER VARYING(12) NOT NULL,
  name  CHARACTER VARYING(100) NOT NULL,
  name_displayed CHARACTER VARYING(100),
  index_heft INT,
  is_used CHARACTER VARYING(100) NOT NULL
);


create table Door_Furniture
(
  id SERIAL PRIMARY KEY,
  idManufacturerProgram CHARACTER VARYING(12) NOT NULL,
  name  CHARACTER VARYING(100) NOT NULL,
  quantity INT,
  itCylinderLock INT,
  isTwoSectionLock INT,
  comment CHARACTER VARYING(100),
  longKey INT,
  bugelHandle INT,
  аrmorLock INT,
  picturePathFirst CHARACTER VARYING(100),
  picturePathSecond CHARACTER VARYING(100),
  sketchPathFirst CHARACTER VARYING(100),
  sketchPathSecond CHARACTER VARYING(100),
  forWarmDoors INT,
  numberOfDoorLeaves INT
);


create table Door_Colors
(
  id SERIAL PRIMARY KEY,
  idManufacturerProgram CHARACTER VARYING(12) NOT NULL,
  name  CHARACTER VARYING(100) NOT NULL,
  picturePathFirst CHARACTER VARYING(100)
);