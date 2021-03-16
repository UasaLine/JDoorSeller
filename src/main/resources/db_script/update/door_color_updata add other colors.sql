ALTER TABLE door_colors ADD COLUMN containsOtherColor INT;
UPDATE door_colors SET containsOtherColor = 0;
