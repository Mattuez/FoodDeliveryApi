package com.matheus.fooddeliveryapi.domain.repository;

import com.matheus.fooddeliveryapi.domain.model.Product;
import com.matheus.fooddeliveryapi.domain.model.ProductPicture;
import com.matheus.fooddeliveryapi.domain.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CustomJpaRepository<Product, Long>{

    Optional<Product> findProductByIdAndRestaurantId(Long id, Long restaurantId);

    List<Product> findProductByRestaurant(Restaurant restaurant);

    @Query("FROM Product p where p.restaurant = :restaurant and p.active = true")
    List<Product> findActiveProductsByRestaurant(Restaurant restaurant);
}
