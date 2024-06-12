package com.matheus.fooddeliveryapi.domain.repository;

import com.matheus.fooddeliveryapi.domain.model.AccessLevel;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessLevelRepository extends CustomJpaRepository<AccessLevel, Long> {
}
