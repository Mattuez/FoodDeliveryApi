package com.matheus.fooddeliveryapi.domain.service;

import com.matheus.fooddeliveryapi.domain.exception.BusinessException;
import com.matheus.fooddeliveryapi.domain.exception.OrderNotFoundException;
import com.matheus.fooddeliveryapi.domain.filter.OrderFilter;
import com.matheus.fooddeliveryapi.domain.model.*;
import com.matheus.fooddeliveryapi.domain.repository.OrderRepository;
import com.matheus.fooddeliveryapi.infraestructure.repository.spec.OrderSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderEmissionService {

    private OrderRepository orderRepository;
    private RestaurantRegistrationService restaurantRegistrationService;
    private PaymentMethodRegistrationService paymentMethodRegistrationService;
    private ProductRegistrationService productRegistrationService;
    private CityRegistrationService cityRegistrationService;
    private UserRegistrationService userRegistrationService;

    public OrderEmissionService(OrderRepository orderRepository,
                                RestaurantRegistrationService restaurantRegistrationService,
                                PaymentMethodRegistrationService paymentMethodRegistrationService,
                                ProductRegistrationService productRegistrationService,
                                CityRegistrationService cityRegistrationService,
                                UserRegistrationService userRegistrationService) {

        this.orderRepository = orderRepository;
        this.restaurantRegistrationService = restaurantRegistrationService;
        this.paymentMethodRegistrationService = paymentMethodRegistrationService;
        this.productRegistrationService = productRegistrationService;
        this.cityRegistrationService = cityRegistrationService;
        this.userRegistrationService = userRegistrationService;
    }

    public List<Order> searchAll(OrderFilter orderFilter) {
        return orderRepository.findAll(OrderSpecs.usingFilter(orderFilter));
    }

    public Page<Order> searchAll(OrderFilter orderFilter, Pageable pageable) {
        return orderRepository.findAll(OrderSpecs.usingFilter(orderFilter), pageable);
    }

    public Order search(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    public Order search(String code) {
        return orderRepository.findByCode(code)
                .orElseThrow(() -> new OrderNotFoundException(code));
    }

    @Transactional
    public Order insert(Order order) {
        validateOrder(order);
        validateItems(order);
        order.calculateTotalAmount();

        return orderRepository.save(order);
    }

    private void validateOrder(Order order) {
        Restaurant restaurant = restaurantRegistrationService.search(order.getRestaurant().getId());
        PaymentMethod paymentMethod = paymentMethodRegistrationService.search(order.getPaymentMethod().getId());
        City city = cityRegistrationService.search(order.getAddress().getCity().getId());
        User user = userRegistrationService.search(order.getClient().getId());

        if (!restaurant.isPaymentMethodAccepted(paymentMethod)) {
            throw new BusinessException("The restaurant of id %d doesn't accept the payment method of id %d"
                    .formatted(restaurant.getId(), paymentMethod.getId()));
        }

        order.setClient(user);
        order.getAddress().setCity(city);
        order.setRestaurant(restaurant);
        order.setPaymentMethod(paymentMethod);
        order.setDeliveryFee(restaurant.getDeliveryFee());
    }

    private void validateItems(Order order) {
        Restaurant restaurant = order.getRestaurant();
        order.getItems()
                .forEach(orderedProduct -> {
                    Product product =
                            productRegistrationService.search(orderedProduct.getProduct().getId(), restaurant.getId());

                    orderedProduct.setOrder(order);
                    orderedProduct.setProduct(product);
                    orderedProduct.setUnitPrice(product.getPrice());
                });
    }
}
