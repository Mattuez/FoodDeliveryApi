CREATE TABLE user_access_level(
    user_id BIGINT NOT NULL,
    access_level_id BIGINT NOT NULL,

    CONSTRAINT fk_user_access_level_user_user_id
        FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_user_access_level_access_level_access_level_id
        FOREIGN KEY (access_level_id) REFERENCES access_level(id),
    CONSTRAINT uc_user_access_level
        UNIQUE (user_id, access_level_id)
) engine = InnoDB default charset=utf8;