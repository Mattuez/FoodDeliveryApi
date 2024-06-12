package com.matheus.fooddeliveryapi.api.assembler.order;

import com.matheus.fooddeliveryapi.api.model.order.OrderInputDto;
import com.matheus.fooddeliveryapi.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoDisassembler {

    private ModelMapper modelMapper;

    public OrderDtoDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Order toEntityObject(OrderInputDto source) {
        return modelMapper.map(source, Order.class);
    }
}
