ALTER TABLE user
    DROP CONSTRAINT fk_user_access_level_access_level_id;

ALTER TABLE user
    DROP COLUMN access_level_id;