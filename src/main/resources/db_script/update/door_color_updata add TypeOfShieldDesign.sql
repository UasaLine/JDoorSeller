ALTER TABLE door_colors ADD COLUMN typeOfShieldDesign CHARACTER VARYING(12);
UPDATE door_colors SET typeOfShieldDesign = null;
