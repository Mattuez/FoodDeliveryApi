package com.matheus.fooddeliveryapi.domain.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantFilter {

    private String name;
    private boolean freeDeliveryFee;
    private boolean verified;
    private Long cuisineId;
    private Long acceptedPaymentMethodId;

}
