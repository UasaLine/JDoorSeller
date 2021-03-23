ALTER TABLE shieldkit
    ADD COLUMN shield_over_color int;
UPDATE shieldkit
SET shield_over_color = NULL;