package com.matheus.fooddeliveryapi.api.model.cuisine;

import com.matheus.fooddeliveryapi.core.validation.annotation.HexadecimalColor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CuisineInputDTO {

        @NotBlank
        private String name;

        @HexadecimalColor
        private String color;
}
