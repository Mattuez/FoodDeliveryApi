create table user(
    id              bigint       not null auto_increment,
    email           varchar(255) not null,
    name            varchar(255) not null,
    password        varchar(255) not null,
    register_date   datetime(6)  not null,
    access_level_id bigint       not null,

    primary key (id),
    constraint fk_user_access_level_access_level_id
        foreign key (access_level_id) references access_level (id)
) engine = InnoDB default charset = utf8;