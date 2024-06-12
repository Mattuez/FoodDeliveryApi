package com.matheus.fooddeliveryapi.domain.repository;

import com.matheus.fooddeliveryapi.domain.model.PaymentMethod;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends CustomJpaRepository<PaymentMethod, Long> {
}
