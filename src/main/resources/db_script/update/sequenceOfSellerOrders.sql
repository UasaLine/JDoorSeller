DROP TABLE seller_sequence;
create table seller_sequence
(
    id        SERIAL PRIMARY KEY,
    seller_id INT,
    order_id  INT
);
