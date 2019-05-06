-- create database
CREATE DATABASE IF NOT EXISTS `test`;
USE test;

-- Create table

DROP TABLE IF EXISTS Door_Class;
create table Door_Class
(
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(100),
  `fireproof` INT(1) NOT NULL,
  `hot` INT(1) NOT NULL
);

DROP TABLE IF EXISTS Limitation_Door;
create table Limitation_Door
(
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `doorClass_id` INT(11) NOT NULL,
  `typeSettings` VARCHAR(100),
  `firstItem` VARCHAR(100),
  `secondItem` VARCHAR(100),
  `startRestriction` INT(11),
  `stopRestriction` INT(11),
  comment VARCHAR(100),
  defaultValue INT(1) NOT NULL
);

DROP TABLE IF EXISTS Door_Type;
create table Door_Type
(
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name  VARCHAR(100) NOT NULL,
  namePicture  VARCHAR(100),
  doorLeaf INT(1) NOT NULL,
  nameForPrint VARCHAR(100),
  nameForPrintInternalOpening VARCHAR(100),
  daysToRelease INT(11) ,
  markUp INT(11) ,
  markUpGlassPackage INT(11),
  DS INT(1)
);


DROP TABLE IF EXISTS Size_Door_Parts;
create table Size_Door_Parts
(
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `doorType_id` INT(11),
  `name`  VARCHAR(100) NOT NULL,
  `condition` VARCHAR(100),
  `width` VARCHAR(100) NOT NULL,
  `height` VARCHAR(100) NOT NULL,
  `quantity` VARCHAR(100) NOT NULL
);


DROP TABLE IF EXISTS Metal;
create table Metal
(
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `id_manufacturer_program` VARCHAR(12) NOT NULL,
  `name`  VARCHAR(100) NOT NULL,
  `name_displayed` VARCHAR(100),
  `index_heft` INT(11),
  `is_used` VARCHAR(100) NOT NULL
);

DROP TABLE IF EXISTS Door_Furniture;
create table Door_Furniture
(
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `idManufacturerProgram` VARCHAR(12) NOT NULL,
  `name`  VARCHAR(100) NOT NULL,
  `quantity` INT(11),
  `itCylinderLock` INT(11),
  `isTwoSectionLock` INT(11),
  `comment` VARCHAR(100),
  `longKey` INT(11),
  `bugelHandle` INT(11),
  `аrmorLock` INT(11),
  `picturePathFirst` VARCHAR(100),
  `picturePathSecond` VARCHAR(100),
  `sketchPathFirst` VARCHAR(100),
  `sketchPathSecond` VARCHAR(100),
  `forWarmDoors` INT(11),
  `numberOfDoorLeaves` INT(11)
);

