create table restaurant(
    id bigint not null auto_increment,
    address_complement varchar(255),
    address_district varchar(255),
    address_name varchar(255),
    address_number varchar(6),
    address_postal_code char(8),
    delivery_fee decimal(19, 2),
    name varchar(255) not null,
    register_date datetime not null,
    update_date datetime not null,
    address_city_id bigint,
    cuisine_id bigint not null,

    primary key (id),
    constraint fk_restaurant_cuisine_cuisine_id
        foreign key (cuisine_id) references cuisine (id),
    constraint fk_restaurant_city_city_id
        foreign key (address_city_id) references city (id)
) engine = InnoDB default charset = utf8;