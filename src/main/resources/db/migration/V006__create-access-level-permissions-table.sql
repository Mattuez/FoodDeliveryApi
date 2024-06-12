create table access_level_permissions(
    access_level_id bigint not null,
    permission_id   bigint not null,

    constraint fk_access_level_permissions_access_level_access_level_id
        foreign key (access_level_id) references access_level(id),
    constraint fk_access_level_permissions_permission_permission_id
        foreign key (permission_id) references permission(id)
) engine = InnoDB default charset=utf8;