package com.matheus.fooddeliveryapi.domain.repository;

import com.matheus.fooddeliveryapi.domain.model.ProductPicture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductPictureRepository extends CustomJpaRepository<ProductPicture, Long>{

    @Query("from ProductPicture pp join pp.product p where p.restaurant.id = :restaurantId and pp.product.id = :productId")
    Optional<ProductPicture> findByProductIdAndRestaurantId(Long productId, Long restaurantId);
}
