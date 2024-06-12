package com.matheus.fooddeliveryapi.domain.repository;

import com.matheus.fooddeliveryapi.domain.model.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends CustomJpaRepository<Permission, Long> {
}
