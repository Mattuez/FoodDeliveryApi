package com.matheus.fooddeliveryapi.api.assembler.product;

import com.matheus.fooddeliveryapi.api.model.products.ProductInputDto;
import com.matheus.fooddeliveryapi.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoDisassembler {

    private ModelMapper modelMapper;

    public ProductDtoDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Product toEntityObject(ProductInputDto source) {
        return modelMapper.map(source, Product.class);
    }

    public void copyToEntityObject(ProductInputDto source, Product destination) {
        modelMapper.map(source, destination);
    }
}
