ALTER TABLE access_level_permissions
    ADD CONSTRAINT uc_access_level_permission UNIQUE (access_level_id, permission_id);