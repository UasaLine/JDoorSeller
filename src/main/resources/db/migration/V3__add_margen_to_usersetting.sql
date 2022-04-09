ALTER TABLE users_setting ADD COLUMN margin_for_change VARCHAR(255);
UPDATE users_setting SET margin_for_change = 0;
