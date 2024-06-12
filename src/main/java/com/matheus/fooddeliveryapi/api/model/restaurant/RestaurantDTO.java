package com.matheus.fooddeliveryapi.api.model.restaurant;

import com.matheus.fooddeliveryapi.api.model.address.AddressDto;
import com.matheus.fooddeliveryapi.api.model.cuisine.CuisineDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDTO {

    private Long id;
    private String name;
    private CuisineDTO cuisine;
    private Boolean active;
    private Boolean opened;
    private AddressDto address;
}
