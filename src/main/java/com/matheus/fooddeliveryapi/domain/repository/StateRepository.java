package com.matheus.fooddeliveryapi.domain.repository;

import com.matheus.fooddeliveryapi.domain.model.State;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends CustomJpaRepository<State, Long> {
}
