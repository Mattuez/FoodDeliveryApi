package com.matheus.fooddeliveryapi.api.model.orderedProduct;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderedProductInputDto {

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

    private String note;
}
