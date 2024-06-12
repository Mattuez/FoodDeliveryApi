package com.matheus.fooddeliveryapi.domain.repository;

import com.matheus.fooddeliveryapi.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestaurantRepository
        extends CustomJpaRepository<Restaurant, Long>, RestaurantRepositoryQueries, JpaSpecificationExecutor<Restaurant> {

    @Query("from Restaurant r join fetch r.cuisine order by r.id")
    List<Restaurant> findAll();

    List<Restaurant> findByDeliveryFeeBetween(BigDecimal inicialDeliveryFee, BigDecimal finalDeliveryFee);

    List<Restaurant> listRestaurantByCuisineId(Long cuisine_id);

    List<Restaurant> find(String name,
                          BigDecimal inicialDeliveryFee, BigDecimal finalDeliveryFee);

    List<Restaurant> findCriteriaApi(String name,
                                     BigDecimal inicialDeliveryFee, BigDecimal finalDeliveryFee);

    List<Restaurant> findWithFreeDeliveryFee(String name);
}
