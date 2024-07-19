create table cuisine_picture
(
    cuisine_id   bigint       not null,
    file_name    varchar(150) not null,
    description  varchar(150),
    content_type varchar(80)  not null,
    size         int          not null,

    primary key (cuisine_id),
    constraint fk_cuisine_picture foreign key (cuisine_id) references cuisine (id)
) engine = InnoDB
  default charset = utf8;