package com.matheus.fooddeliveryapi.domain.service;

import com.matheus.fooddeliveryapi.domain.exception.ProductNotFoundException;
import com.matheus.fooddeliveryapi.domain.model.Product;
import com.matheus.fooddeliveryapi.domain.model.Restaurant;
import com.matheus.fooddeliveryapi.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductRegistrationService {

    private ProductRepository productRepository;
    private RestaurantRegistrationService restaurantRegistrationService;

    public ProductRegistrationService(ProductRepository productRepository, RestaurantRegistrationService restaurantRegistrationService) {
        this.productRepository = productRepository;
        this.restaurantRegistrationService = restaurantRegistrationService;
    }

    public List<Product> searchByRestaurant(Restaurant restaurant, boolean includeInactive) {
        if (includeInactive) {
            return searchAllByRestaurant(restaurant);
        }

        return searchActiveByRestaurant(restaurant);
    }

    private List<Product> searchAllByRestaurant(Restaurant restaurant) {
        return productRepository.findProductByRestaurant(restaurant);
    }

    private List<Product> searchActiveByRestaurant(Restaurant restaurant) {
        return productRepository.findActiveProductsByRestaurant(restaurant);
    }

    public Product search(Long productId, Long restaurantId) {
        return productRepository.findProductByIdAndRestaurantId(productId, restaurantId)
                .orElseThrow(() -> new ProductNotFoundException(productId, restaurantId));
    }

    public Product insert(Product product) {
        return productRepository.save(product);
    }
}
