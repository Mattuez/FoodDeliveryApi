package com.matheus.fooddeliveryapi.api.assembler.picture;

import com.matheus.fooddeliveryapi.api.model.picture.PictureDto;
import com.matheus.fooddeliveryapi.domain.model.Picture;
import com.matheus.fooddeliveryapi.domain.model.ProductPicture;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PictureDtoAssembler<T extends Picture> {

    private ModelMapper modelMapper;

    public PictureDtoAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PictureDto toDto(T source) {
        return modelMapper.map(source, PictureDto.class);
    }
}
