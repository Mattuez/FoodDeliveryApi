package com.matheus.fooddeliveryapi.domain.repository;

import com.matheus.fooddeliveryapi.domain.model.OrderedProduct;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedProductRepository extends CustomJpaRepository<OrderedProduct, Long> {

    List<OrderedProduct> findOrderedProductByOrderId(Long orderId);
}
