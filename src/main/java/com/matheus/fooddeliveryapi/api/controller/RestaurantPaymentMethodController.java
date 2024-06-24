package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.paymentMethod.PaymentMethodDtoAssembler;
import com.matheus.fooddeliveryapi.api.model.paymentMethod.PaymentMethodDto;
import com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation.RestaurantPaymentMethodOpenApi;
import com.matheus.fooddeliveryapi.core.security.CheckSecurity;
import com.matheus.fooddeliveryapi.domain.model.Restaurant;
import com.matheus.fooddeliveryapi.domain.service.RestaurantRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/paymentMethods")
public class RestaurantPaymentMethodController implements RestaurantPaymentMethodOpenApi {

    private RestaurantRegistrationService restaurantService;
    private PaymentMethodDtoAssembler paymentMethodDtoAssembler;

    public RestaurantPaymentMethodController(RestaurantRegistrationService restaurantService,
                                             PaymentMethodDtoAssembler paymentMethodDtoAssembler) {
        this.restaurantService = restaurantService;
        this.paymentMethodDtoAssembler = paymentMethodDtoAssembler;
    }

    @CheckSecurity.Restaurant.canView
    @GetMapping
    public List<PaymentMethodDto> getAllByRestaurantId(@PathVariable("restaurantId") Long restaurantId) {
        Restaurant restaurant = restaurantService.search(restaurantId);

        return paymentMethodDtoAssembler.toCollectionDto(restaurant.getPaymentMethods());
    }

    @CheckSecurity.Restaurant.canManageOperation
    @PutMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associate(@PathVariable("restaurantId") Long restaurantId,
                          @PathVariable("paymentMethodId") Long paymentMethodId) {
        restaurantService.associatePaymentMethod(restaurantId, paymentMethodId);
    }

    @CheckSecurity.Restaurant.canManageOperation
    @DeleteMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable("restaurantId") Long restaurantId,
                          @PathVariable("paymentMethodId") Long paymentMethodId) {
        restaurantService.disassociatePaymentMethod(restaurantId, paymentMethodId);
    }
}
