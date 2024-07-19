create table restaurant_picture
(
    restaurant_id   bigint       not null,
    file_name    varchar(150) not null,
    description  varchar(150),
    content_type varchar(80)  not null,
    size         int          not null,

    primary key (restaurant_id),
    constraint fk_restaurant_picture foreign key (restaurant_id) references restaurant (id)
) engine = InnoDB
  default charset = utf8;