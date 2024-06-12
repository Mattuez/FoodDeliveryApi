package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.order.OrderDtoAssembler;
import com.matheus.fooddeliveryapi.api.assembler.order.OrderDtoDisassembler;
import com.matheus.fooddeliveryapi.api.assembler.order.OrderResumeDtoAssembler;
import com.matheus.fooddeliveryapi.api.model.order.OrderDto;
import com.matheus.fooddeliveryapi.api.model.order.OrderInputDto;
import com.matheus.fooddeliveryapi.api.model.order.OrderResumeDto;
import com.matheus.fooddeliveryapi.core.data.PageableTranslator;
import com.matheus.fooddeliveryapi.domain.filter.OrderFilter;
import com.matheus.fooddeliveryapi.domain.model.Order;
import com.matheus.fooddeliveryapi.domain.service.OrderEmissionService;
import com.matheus.fooddeliveryapi.domain.service.UserRegistrationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private UserRegistrationService userRegistrationService;
    private OrderEmissionService orderEmissionService;
    private OrderDtoAssembler orderDtoAssembler;
    private OrderResumeDtoAssembler orderResumeDtoAssembler;
    private OrderDtoDisassembler orderDtoDisassembler;

    public OrderController(OrderEmissionService orderEmissionService, OrderDtoAssembler orderDtoAssembler,
                           OrderResumeDtoAssembler orderResumeDtoAssembler, OrderDtoDisassembler orderDtoDisassembler,
                           UserRegistrationService userRegistrationService) {
        this.orderEmissionService = orderEmissionService;
        this.orderDtoAssembler = orderDtoAssembler;
        this.orderResumeDtoAssembler = orderResumeDtoAssembler;
        this.orderDtoDisassembler = orderDtoDisassembler;
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping
    public Page<OrderResumeDto> getAll(OrderFilter orderFilter, @PageableDefault(size = 10) Pageable pageable) {

        Page<Order> orderPage = orderEmissionService.searchAll(orderFilter, pageable);

        List<OrderResumeDto> orderDtoList = orderResumeDtoAssembler.toDtoCollection(orderPage.getContent());

        return new PageImpl<>(orderDtoList, pageable, orderPage.getTotalElements());
    }

    @GetMapping("/{orderId}")
    public OrderDto getById(@PathVariable("orderId") String code) {
        return orderDtoAssembler.toDto(orderEmissionService.search(code));
    }

    @PostMapping
    public OrderDto add(@RequestBody @Valid OrderInputDto orderInputDto) {
        Order order = orderDtoDisassembler.toEntityObject(orderInputDto);

        order.setClient(userRegistrationService.search(1L));

        return orderDtoAssembler.toDto(orderEmissionService.insert(order));
    }

    private Pageable translatePageable(Pageable pageable) {
        Map<String, String> fieldMap = Map.of("code", "code",
                "subtotal", "subtotal",
                "deliveryFee", "deliveryFee",
                "totalAmount", "totalAmount",
                "status", "status",
                "creationDate", "creationDate",
                "restaurant", "restaurant",
                "client", "client");

        return PageableTranslator.translate(pageable, fieldMap);
    }
}
