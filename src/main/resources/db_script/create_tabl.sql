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

