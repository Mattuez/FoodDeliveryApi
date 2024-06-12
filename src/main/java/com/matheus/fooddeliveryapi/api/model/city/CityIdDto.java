package com.matheus.fooddeliveryapi.api.model.city;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityIdDto {

    @NotNull
    private Long id;
}
