create table ordered_product(
    id          bigint         not null auto_increment,
    quantity    integer        not null,
    total_price decimal(19, 2) not null,
    unit_price  decimal(19, 2) not null,
    order_id    bigint         not null,
    product_id  bigint         not null,

    primary key (id),
    constraint fk_ordered_product_order_order_id
        foreign key (order_id) references `order` (id),
    constraint fk_ordered_product_product_product_id
        foreign key (product_id) references product (id)
) engine = InnoDB default charset = utf8;