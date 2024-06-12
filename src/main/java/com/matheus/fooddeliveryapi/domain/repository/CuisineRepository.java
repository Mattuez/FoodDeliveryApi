package com.matheus.fooddeliveryapi.domain.repository;

import com.matheus.fooddeliveryapi.domain.model.Cuisine;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuisineRepository extends CustomJpaRepository<Cuisine, Long> {

    List<Cuisine> findByName(String name);

    List<Cuisine> findCuisineByNameContaining(String name);
}
