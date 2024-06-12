package com.matheus.fooddeliveryapi.api.assembler.order;

import com.matheus.fooddeliveryapi.api.model.order.OrderDto;
import com.matheus.fooddeliveryapi.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDtoAssembler {

    private ModelMapper modelMapper;

    public OrderDtoAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderDto toDto(Order source) {
        return modelMapper.map(source, OrderDto.class);
    }

    public List<OrderDto> toDtoCollection(List<Order> source) {
        return source.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
