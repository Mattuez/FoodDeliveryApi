create table restaurant_payment_methods(
    restaurant_id     bigint not null,
    payment_method_id bigint not null,

    constraint fk_restaurant_payment_methods_restaurant_restaurant_id
        foreign key (restaurant_id) references restaurant(id),
    constraint fk_restaurant_payment_methods_payment_method_payment_method_id
        foreign key (payment_method_id) references payment_method(id)
) engine = InnoDB default charset=utf8;