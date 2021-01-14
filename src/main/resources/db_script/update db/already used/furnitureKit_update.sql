ALTER TABLE furniturekit ADD COLUMN peephole_position VARCHAR(255);
UPDATE furniturekit SET peephole_position = 'CENTER';
UPDATE furniturekit SET peephole = null;
