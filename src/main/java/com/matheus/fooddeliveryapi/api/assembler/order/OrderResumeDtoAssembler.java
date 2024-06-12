package com.matheus.fooddeliveryapi.api.assembler.order;

import com.matheus.fooddeliveryapi.api.model.order.OrderResumeDto;
import com.matheus.fooddeliveryapi.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderResumeDtoAssembler {

    private ModelMapper modelMapper;

    public OrderResumeDtoAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderResumeDto toDto(Order source) {
        return modelMapper.map(source, OrderResumeDto.class);
    }

    public List<OrderResumeDto> toDtoCollection(List<Order> source) {
        return source.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
