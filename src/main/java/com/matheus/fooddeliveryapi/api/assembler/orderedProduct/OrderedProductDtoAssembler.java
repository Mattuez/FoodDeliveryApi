package com.matheus.fooddeliveryapi.api.assembler.orderedProduct;

import com.matheus.fooddeliveryapi.api.model.orderedProduct.OrderedProductDto;
import com.matheus.fooddeliveryapi.domain.model.OrderedProduct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderedProductDtoAssembler {

    private ModelMapper modelMapper;

    public OrderedProductDtoAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderedProductDto toDto(OrderedProduct source) {
        return modelMapper.map(source, OrderedProductDto.class);
    }

    public List<OrderedProductDto> toDtoCollection(List<OrderedProduct> source) {
        return source.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
