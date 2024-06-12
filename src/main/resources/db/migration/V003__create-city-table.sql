create table city(
    id bigint not null auto_increment,
    name varchar(60) not null,
    state_id bigint not null,

    primary key (id),
    constraint fk_city_state_state_id
        foreign key(state_id) references state(id)

) engine=innoDB default charset=utf8;