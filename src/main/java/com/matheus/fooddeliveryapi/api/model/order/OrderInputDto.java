package com.matheus.fooddeliveryapi.api.model.order;

import com.matheus.fooddeliveryapi.api.model.address.AddressInputDto;
import com.matheus.fooddeliveryapi.api.model.orderedProduct.OrderedProductInputDto;
import com.matheus.fooddeliveryapi.api.model.paymentMethod.PaymentMethodIdDto;
import com.matheus.fooddeliveryapi.api.model.restaurant.RestaurantIdDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class OrderInputDto {

    @Valid
    @NotNull
    private RestaurantIdDto restaurant;

    @Valid
    @NotNull
    private PaymentMethodIdDto paymentMethod;

    @Valid
    @NotNull
    private AddressInputDto addressToBeDelivered;

    @Valid
    @NotEmpty
    List<OrderedProductInputDto> items;
}
