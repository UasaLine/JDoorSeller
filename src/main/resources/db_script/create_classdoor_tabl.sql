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
  `fireproof` INT(11) NOT NULL,
  `hot` INT(11) NOT NULL
);