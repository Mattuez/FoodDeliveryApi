package com.matheus.fooddeliveryapi.domain.service;

import com.matheus.fooddeliveryapi.domain.exception.EntityBeingUsedException;
import com.matheus.fooddeliveryapi.domain.exception.RestaurantNotFoundException;
import com.matheus.fooddeliveryapi.domain.model.*;
import com.matheus.fooddeliveryapi.domain.repository.RestaurantRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantRegistrationService {

    public static final String MSG_RESTAURANT_BEING_USED = "Restaurant with code %d is being used and can't be removed";

    private RestaurantRepository restaurantRepository;
    private CuisineRegistrationService cuisineRegistrationService;
    private CityRegistrationService cityRegistrationService;
    private PaymentMethodRegistrationService paymentMethodRegistrationService;
    private UserRegistrationService userRegistrationService;

    public RestaurantRegistrationService(RestaurantRepository restaurantRepository,
                                         CuisineRegistrationService cuisineRegistrationService,
                                         CityRegistrationService cityRegistrationService,
                                         PaymentMethodRegistrationService paymentMethodRegistrationService,
                                         UserRegistrationService userRegistrationService) {
        this.restaurantRepository = restaurantRepository;
        this.cuisineRegistrationService = cuisineRegistrationService;
        this.cityRegistrationService = cityRegistrationService;
        this.paymentMethodRegistrationService = paymentMethodRegistrationService;
        this.userRegistrationService = userRegistrationService;
    }

    public List<Restaurant> searchAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant search(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    }

    @Transactional
    public Restaurant insert(Restaurant restaurant) {
        Long cuisineId = restaurant.getCuisine().getId();
        Long cityId =  restaurant.getAddress().getCity().getId();

        Cuisine cuisine = cuisineRegistrationService.search(cuisineId);
        City city = cityRegistrationService.search(cityId);

        restaurant.setCuisine(cuisine);
        restaurant.getAddress().setCity(city);

        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void exclude(Long restaurantId) {
        try {
            restaurantRepository.deleteById(restaurantId);
            restaurantRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new RestaurantNotFoundException(restaurantId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityBeingUsedException(
                    String.format(MSG_RESTAURANT_BEING_USED, restaurantId)
            );
        }
    }

    @Transactional
    public void activate(Long restaurantId) {
        Restaurant restaurant = search(restaurantId);

        restaurant.activate();
    }

    @Transactional
    public void deactivate(Long restaurantId) {
        Restaurant restaurant = search(restaurantId);

        restaurant.deactivate();
    }

    @Transactional
    public void activate(List<Long> restaurantIds) {
        restaurantIds.forEach(this::activate);
    }

    @Transactional
    public void deactivate(List<Long> restaurantIds) {
        restaurantIds.forEach(this::deactivate);
    }

    @Transactional
    public void associatePaymentMethod(Long restaurantId, Long paymentMethodId) {
        PaymentMethod paymentMethod = paymentMethodRegistrationService.search(paymentMethodId);
        Restaurant restaurant = search(restaurantId);

        restaurant.addPaymentMethod(paymentMethod);
    }

    @Transactional
    public void disassociatePaymentMethod(Long restaurantId, Long paymentMethodId) {
        PaymentMethod paymentMethod = paymentMethodRegistrationService.search(paymentMethodId);
        Restaurant restaurant = search(restaurantId);

        restaurant.removePaymentMethod(paymentMethod);
    }

    @Transactional
    public void openRestaurant(Long restaurantId) {
        Restaurant restaurant = search(restaurantId);

        restaurant.open();
    }

    @Transactional
    public void closeRestaurant(Long restaurantId) {
        Restaurant restaurant = search(restaurantId);

        restaurant.close();
    }

    @Transactional
    public void associateAdmin(Long restaurantId, Long userId) {
        Restaurant restaurant = search(restaurantId);
        User admin = userRegistrationService.search(userId);

        restaurant.addAdmin(admin);
    }

    @Transactional
    public void disassociateAdmin(Long restaurantId, Long userId) {
        Restaurant restaurant = search(restaurantId);
        User admin = userRegistrationService.search(userId);

        restaurant.removeAdmin(admin);
    }
}
