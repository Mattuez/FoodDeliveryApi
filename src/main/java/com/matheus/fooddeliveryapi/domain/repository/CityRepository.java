package com.matheus.fooddeliveryapi.domain.repository;

import com.matheus.fooddeliveryapi.domain.model.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends CustomJpaRepository<City, Long> {

    @Query("from City c join fetch c.state")
    List<City> findAll();
}
