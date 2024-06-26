package com.matheus.fooddeliveryapi.domain.repository;

import com.matheus.fooddeliveryapi.domain.model.Order;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("from Order o join fetch o.client join fetch o.restaurant r join fetch r.cuisine")
    List<Order> findAll();

    Optional<Order> findByCode(String code);

    boolean existsByClientId(Long clientId);

    boolean isAdminFromOrder(String code, Long userId);
}
