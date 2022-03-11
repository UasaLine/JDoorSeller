ALTER TABLE design
    ADD COLUMN out_shield_design int;
UPDATE design
SET out_shield_design = NULL;
