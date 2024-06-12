package com.matheus.fooddeliveryapi.domain.repository;

import com.matheus.fooddeliveryapi.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepositoryQueries {
    List<Restaurant> find(String name, BigDecimal inicialDeliveryFee, BigDecimal finalDeliveryFee);
    List<Restaurant> findCriteriaApi(String name, BigDecimal inicialDeliveryFee, BigDecimal finalDeliveryFee);
    List<Restaurant> findWithFreeDeliveryFee(String name);
}
