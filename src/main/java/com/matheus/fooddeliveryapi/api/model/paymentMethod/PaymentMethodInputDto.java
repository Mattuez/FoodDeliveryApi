package com.matheus.fooddeliveryapi.api.model.paymentMethod;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PaymentMethodInputDto {

    @NotBlank
    private String description;
}
