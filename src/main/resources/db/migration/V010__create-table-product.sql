create table product(
    id bigint not null auto_increment,
    active bit not null,
    description varchar(255) not null,
    name varchar(30) not null,
    price decimal(19, 2) not null,
    restaurant_id bigint not null,

    primary key (id),
    constraint fk_product_restaurant_restaurant_id
        foreign key (restaurant_id) references restaurant (id)
) engine = InnoDB default charset = utf8;