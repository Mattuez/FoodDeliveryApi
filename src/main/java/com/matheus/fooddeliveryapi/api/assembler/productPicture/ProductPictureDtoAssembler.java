package com.matheus.fooddeliveryapi.api.assembler.productPicture;

import com.matheus.fooddeliveryapi.api.model.products.picture.ProductPictureDto;
import com.matheus.fooddeliveryapi.domain.model.ProductPicture;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductPictureDtoAssembler {

    private ModelMapper modelMapper;

    public ProductPictureDtoAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductPictureDto toDto(ProductPicture source) {
        return modelMapper.map(source, ProductPictureDto.class);
    }
}
