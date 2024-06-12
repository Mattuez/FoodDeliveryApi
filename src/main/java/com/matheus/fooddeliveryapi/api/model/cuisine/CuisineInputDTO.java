package com.matheus.fooddeliveryapi.api.model.cuisine;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CuisineInputDTO {

        @NotBlank
        private String name;
}
