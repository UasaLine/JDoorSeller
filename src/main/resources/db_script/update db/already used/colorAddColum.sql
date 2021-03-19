ALTER TABLE Door_Colors
    ADD COLUMN containsWoodPanel int;
UPDATE Door_Colors
SET containsWoodPanel = 0;

ALTER TABLE Door_Colors
    ADD COLUMN maskPath CHARACTER VARYING(100);
UPDATE Door_Colors
SET maskPath = null;
