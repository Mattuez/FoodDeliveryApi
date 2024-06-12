package com.matheus.fooddeliveryapi.api.model.order;

import com.matheus.fooddeliveryapi.api.model.address.AddressDto;
import com.matheus.fooddeliveryapi.api.model.orderedProduct.OrderedProductDto;
import com.matheus.fooddeliveryapi.api.model.paymentMethod.PaymentMethodDto;
import com.matheus.fooddeliveryapi.api.model.restaurant.RestaurantResumeDto;
import com.matheus.fooddeliveryapi.api.model.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDto {

    private String code;
    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal totalAmount;
    private AddressDto address;
    private String status;
    private OffsetDateTime creationDate;
    private OffsetDateTime confirmationDate;
    private OffsetDateTime deliveryDate;
    private OffsetDateTime cancellationDate;
    private PaymentMethodDto paymentMethod;
    private RestaurantResumeDto restaurant;
    private List<OrderedProductDto> items;
    private UserDto client;
}
