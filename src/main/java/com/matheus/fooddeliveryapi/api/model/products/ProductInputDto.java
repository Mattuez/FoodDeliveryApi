package com.matheus.fooddeliveryapi.api.model.products;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductInputDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @PositiveOrZero
    private BigDecimal price;

    @NotNull
    private Boolean active;
}
