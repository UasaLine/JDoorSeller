DROP TABLE limiting_colors;
create table limiting_colors
(
    id          SERIAL PRIMARY KEY,
    master_id INT,
    slave_id  INT
);
