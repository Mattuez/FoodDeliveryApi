create table `order`
(
    id                  bigint         not null auto_increment,
    address_complement  varchar(255),
    address_district    varchar(255),
    address_name        varchar(255),
    address_number      varchar(255),
    address_postal_code varchar(255),
    cancellation_date   datetime(6),
    confirmation_date   datetime(6),
    creation_date       datetime(6)    not null,
    delivery_date       datetime(6),
    delivery_fee        decimal(19, 2) not null,
    order_status        integer,
    subtotal            decimal(19, 2) not null,
    total_amount        decimal(19, 2) not null,
    address_city_id     bigint,
    payment_method_id   bigint         not null,
    restaurant_id       bigint         not null,
    user_id             bigint         not null,

    primary key (id),
    constraint fk_order_city_city_id
        foreign key (address_city_id) references city (id),
    constraint fk_order_payment_method_payment_method_id
        foreign key (payment_method_id) references payment_method (id),
    constraint fk_order_restaurant_restaurant_id
        foreign key (restaurant_id) references restaurant (id),
    constraint fk_order_user_user_id
        foreign key (user_id) references user (id)
) engine = InnoDB default charset = utf8;