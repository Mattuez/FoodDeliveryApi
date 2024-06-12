create table payment_method(
    id bigint not null auto_increment,
    description varchar(30) not null,
    primary key (id)
) engine = InnoDB default charset = utf8;