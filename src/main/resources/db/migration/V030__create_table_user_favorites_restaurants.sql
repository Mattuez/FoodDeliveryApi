create table user_favorites_restaurants
(
    use_id        bigint not null,
    restaurant_id bigint not null,

    primary key (use_id, restaurant_id),
    constraint fk_user_favorites_restaurants_restaurants_restaurants_id
        foreign key (restaurant_id) references restaurant (id),
    constraint fk_user_favorites_restaurants_user_user_id
        foreign key (use_id) references user (id)
) engine = InnoDB;