package com.matheus.fooddeliveryapi.domain.service;

import com.matheus.fooddeliveryapi.domain.model.Order;
import com.matheus.fooddeliveryapi.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderTransitionStatusService {

    private OrderEmissionService orderEmissionService;
    private OrderRepository orderRepository;

    public OrderTransitionStatusService(OrderEmissionService orderEmissionService, OrderRepository orderRepository) {
        this.orderEmissionService = orderEmissionService;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void confirm(String code) {
        Order order = orderEmissionService.search(code);
        order.confirm();

        orderRepository.save(order);
    }

    @Transactional
    public void deliver(String code) {
        Order order = orderEmissionService.search(code);

        order.deliver();
    }

    @Transactional
    public void cancel(String code) {
        Order order = orderEmissionService.search(code);
        order.cancel();

        orderRepository.save(order);
    }
}
