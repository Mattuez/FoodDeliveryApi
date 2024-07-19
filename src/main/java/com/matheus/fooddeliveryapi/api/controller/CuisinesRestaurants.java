package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.restaurant.RestaurantDTOAssembler;
import com.matheus.fooddeliveryapi.api.model.restaurant.RestaurantDTO;
import com.matheus.fooddeliveryapi.domain.model.Cuisine;
import com.matheus.fooddeliveryapi.domain.service.CuisineRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cuisines/{cuisineId}/restaurants")
public class CuisinesRestaurants {

    private CuisineRegistrationService cuisineRegistrationService;
    private RestaurantDTOAssembler restaurantDTOAssembler;

    @GetMapping
    public List<RestaurantDTO> getAllByCuisine(@PathVariable("cuisineId") Long cuisineId) {
        Cuisine cuisine = cuisineRegistrationService.search(cuisineId);

        return restaurantDTOAssembler.toCollectionDTO(cuisine.getRestaurants());
    }
}
