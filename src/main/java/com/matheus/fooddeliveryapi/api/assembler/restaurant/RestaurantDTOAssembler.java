package com.matheus.fooddeliveryapi.api.assembler.restaurant;

import com.matheus.fooddeliveryapi.api.model.restaurant.RestaurantDTO;
import com.matheus.fooddeliveryapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantDTOAssembler {

    private ModelMapper modelMapper;

    public RestaurantDTOAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RestaurantDTO toDTO(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    public List<RestaurantDTO> toCollectionDTO(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(restaurant -> toDTO(restaurant))
                .toList();
    }
}
