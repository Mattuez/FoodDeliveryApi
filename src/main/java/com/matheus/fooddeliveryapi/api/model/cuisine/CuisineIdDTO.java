package com.matheus.fooddeliveryapi.api.model.cuisine;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CuisineIdDTO {

    @NotNull
    private Long id;
}
