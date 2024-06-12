ALTER TABLE `order`
    DROP COLUMN order_status;

ALTER TABLE `order`
    ADD COLUMN order_status ENUM('CREATED', 'CONFIRMED', 'DELIVERED', 'CANCELED');