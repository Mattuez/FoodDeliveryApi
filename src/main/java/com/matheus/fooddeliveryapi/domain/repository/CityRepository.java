package com.matheus.fooddeliveryapi.domain.repository;

import com.matheus.fooddeliveryapi.domain.model.City;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CustomJpaRepository<City, Long> {
}
