ALTER TABLE Door_Colors
    ADD COLUMN peepholeOnlyEdge int;
UPDATE Door_Colors
SET peepholeOnlyEdge = 0;
