ALTER TABLE design
    ADD COLUMN out_shield int;
UPDATE design
SET out_shield = NULL;