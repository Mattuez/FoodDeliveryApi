package com.matheus.fooddeliveryapi.api.model.restaurant;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RestaurantIdDto {

    @NotNull
    private Long id;
}
