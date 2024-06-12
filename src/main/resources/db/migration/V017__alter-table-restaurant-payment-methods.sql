ALTER TABLE restaurant_payment_methods
    ADD CONSTRAINT uc_restaurant_payment_method UNIQUE (restaurant_id, payment_method_id);