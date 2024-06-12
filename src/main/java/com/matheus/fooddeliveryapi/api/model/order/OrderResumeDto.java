package com.matheus.fooddeliveryapi.api.model.order;

import com.matheus.fooddeliveryapi.api.model.restaurant.RestaurantResumeDto;
import com.matheus.fooddeliveryapi.api.model.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class OrderResumeDto {

    private String code;
    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal totalAmount;
    private String status;
    private OffsetDateTime creationDate;
    private RestaurantResumeDto restaurant;
    private UserDto client;
}
