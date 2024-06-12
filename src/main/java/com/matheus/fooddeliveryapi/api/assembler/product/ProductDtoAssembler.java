package com.matheus.fooddeliveryapi.api.assembler.product;

import com.matheus.fooddeliveryapi.api.model.products.ProductDto;
import com.matheus.fooddeliveryapi.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDtoAssembler {

    private ModelMapper modelMapper;

    public ProductDtoAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductDto toDto(Product source) {
        return modelMapper.map(source, ProductDto.class);
    }

    public List<ProductDto> toCollectionDto(List<Product> source) {
        return source.stream()
                .map(item -> toDto(item))
                .collect(Collectors.toList());
    }
}
