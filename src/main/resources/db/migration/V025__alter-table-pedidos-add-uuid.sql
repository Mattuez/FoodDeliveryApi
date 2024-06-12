ALTER TABLE `order` ADD code VARCHAR(36) NOT NULL AFTER id;
ALTER TABLE `order` ADD CONSTRAINT uk_order_code UNIQUE (code);

UPDATE `order` SET code = uuid();