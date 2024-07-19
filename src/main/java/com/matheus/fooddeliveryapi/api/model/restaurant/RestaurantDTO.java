package com.matheus.fooddeliveryapi.api.model.restaurant;

import com.matheus.fooddeliveryapi.api.model.address.AddressDto;
import com.matheus.fooddeliveryapi.api.model.cuisine.CuisineDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantDTO {

    private Long id;
    private String name;
    private BigDecimal deliveryFee;
    private CuisineDTO cuisine;
    private Boolean active;
    private Boolean opened;
    private Boolean verified;
    private AddressDto address;
}
