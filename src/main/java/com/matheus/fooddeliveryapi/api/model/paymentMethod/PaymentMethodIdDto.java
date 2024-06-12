package com.matheus.fooddeliveryapi.api.model.paymentMethod;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PaymentMethodIdDto {

    @NotNull
    private Long id;
}
