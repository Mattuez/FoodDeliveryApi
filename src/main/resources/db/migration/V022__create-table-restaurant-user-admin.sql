CREATE TABLE restaurant_user_admin(
    restaurant_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_restaurant_user_admin_restaurant_restaurant_id
        FOREIGN KEY (restaurant_id) REFERENCES restaurant(id),
    CONSTRAINT fk_restaurant_user_admin_user_user_id
        FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT uc_restaurant_user_admin
        UNIQUE (restaurant_id, user_id)
);