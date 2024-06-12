create table permission(
    id bigint not null auto_increment,
    name varchar(30)  not null,
    description varchar(255) not null,

    primary key (id)
) engine = InnoDB default charset=utf8;