package com.matheus.fooddeliveryapi.core.security;

import com.matheus.fooddeliveryapi.domain.repository.OrderRepository;
import com.matheus.fooddeliveryapi.domain.repository.RestaurantRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSecurity {

    private RestaurantRepository restaurantRepository;
    private OrderRepository orderRepository;

    public AuthenticationSecurity(RestaurantRepository restaurantRepository, OrderRepository orderRepository) {
        this.restaurantRepository = restaurantRepository;
        this.orderRepository = orderRepository;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();

        return jwt.getClaim("user_id");
    }

    public boolean manageRestaurant(Long restaurantId) {
        return restaurantRepository.existsAdmins(restaurantId, getUserId());
    }

    public boolean hasOrder(Long clientId) {
        return orderRepository.existsByClientId(clientId);
    }

    public boolean isAdminFromRestaurantOrder(String orderCode) {
        return orderRepository.isAdminFromOrder(orderCode, getUserId());
    }
}
