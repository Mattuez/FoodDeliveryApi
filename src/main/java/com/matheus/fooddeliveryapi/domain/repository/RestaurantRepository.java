package com.matheus.fooddeliveryapi.domain.repository;

import com.matheus.fooddeliveryapi.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestaurantRepository extends CustomJpaRepository<Restaurant, Long>,
        RestaurantRepositoryQueries, JpaSpecificationExecutor<Restaurant> {

    @Query("from Restaurant r join fetch r.cuisine join fetch r.address.city join fetch r.address.city.state order by r.id")
    List<Restaurant> findAll();

    boolean existsAdmins(Long restaurantId, Long userId);
}
