package com.matheus.fooddeliveryapi.api.assembler.restaurant;

import com.matheus.fooddeliveryapi.api.model.restaurant.RestaurantInputDTO;
import com.matheus.fooddeliveryapi.domain.model.City;
import com.matheus.fooddeliveryapi.domain.model.Cuisine;
import com.matheus.fooddeliveryapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestaurantInputDTODisassembler {

    private ModelMapper modelMapper;

    public RestaurantInputDTODisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Restaurant toDomainObject(RestaurantInputDTO restaurantInputDTO) {
        return modelMapper.map(restaurantInputDTO, Restaurant.class);
    }

    public void copyToDomainObject(RestaurantInputDTO restaurantInputDTO, Restaurant restaurant) {
        restaurant.setCuisine(new Cuisine());

        if (restaurant.getAddress() != null) {
            restaurant.getAddress().setCity(new City());
        }

        modelMapper.map(restaurantInputDTO, restaurant);
    }
}
