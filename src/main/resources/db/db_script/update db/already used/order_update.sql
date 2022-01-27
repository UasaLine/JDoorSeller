ALTER TABLE orders ADD COLUMN seller_order_id int;
UPDATE orders SET seller_order_id = 0;
