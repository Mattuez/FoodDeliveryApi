package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.user.UserDtoAssembler;
import com.matheus.fooddeliveryapi.api.model.user.UserDto;
import com.matheus.fooddeliveryapi.domain.model.Restaurant;
import com.matheus.fooddeliveryapi.domain.service.RestaurantRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/admins")
public class RestaurantAdminController {

    private RestaurantRegistrationService restaurantRegistrationService;
    private UserDtoAssembler userDtoAssembler;

    public RestaurantAdminController(RestaurantRegistrationService restaurantRegistrationService,
                                     UserDtoAssembler userDtoAssembler) {

        this.restaurantRegistrationService = restaurantRegistrationService;
        this.userDtoAssembler = userDtoAssembler;
    }

    @GetMapping
    public List<UserDto> getAllByRestaurantId(@PathVariable("restaurantId") Long restaurantId) {
        Restaurant restaurant = restaurantRegistrationService.search(restaurantId);

        return userDtoAssembler.toDtoCollection(restaurant.getAdmins());
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associate(@PathVariable("restaurantId") Long restaurantId, @PathVariable("userId") Long userId) {
        restaurantRegistrationService.associateAdmin(restaurantId, userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable("restaurantId") Long restaurantId, @PathVariable("userId") Long userId) {
        restaurantRegistrationService.disassociateAdmin(restaurantId, userId);
    }
}
