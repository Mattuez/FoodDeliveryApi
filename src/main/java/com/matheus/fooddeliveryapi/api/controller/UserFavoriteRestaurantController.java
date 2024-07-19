package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.restaurant.RestaurantDTOAssembler;
import com.matheus.fooddeliveryapi.api.model.restaurant.RestaurantDTO;
import com.matheus.fooddeliveryapi.core.security.AuthenticationSecurity;
import com.matheus.fooddeliveryapi.domain.model.Restaurant;
import com.matheus.fooddeliveryapi.domain.model.User;
import com.matheus.fooddeliveryapi.domain.service.RestaurantRegistrationService;
import com.matheus.fooddeliveryapi.domain.service.UserRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/favorites-restaurants")
public class UserFavoriteRestaurantController {

    private UserRegistrationService userRegistrationService;
    private RestaurantDTOAssembler restaurantDTOAssembler;
    private RestaurantRegistrationService restaurantRegistrationService;
    private AuthenticationSecurity authenticationSecurity;

    @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
    @GetMapping
    public List<RestaurantDTO> getAllByUserId() {
        Long userId = authenticationSecurity.getUserId();
        User user = userRegistrationService.search(userId);

        return restaurantDTOAssembler.toCollectionDTO(user.getFavoritesRestaurants());
    }

    @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
    @PutMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void favoriteRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        Long userId = authenticationSecurity.getUserId();
        Restaurant restaurant = restaurantRegistrationService.search(restaurantId);

        userRegistrationService.addFavoriteRestaurant(userId, restaurant);
    }

    @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfavoriteRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        Long userId = authenticationSecurity.getUserId();
        Restaurant restaurant = restaurantRegistrationService.search(restaurantId);

        userRegistrationService.removeFavoriteRestaurant(userId, restaurant);
    }
}
