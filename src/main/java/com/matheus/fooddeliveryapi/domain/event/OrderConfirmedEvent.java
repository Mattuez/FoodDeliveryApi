package com.matheus.fooddeliveryapi.domain.event;


import com.matheus.fooddeliveryapi.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderConfirmedEvent {

    private Order order;

}
