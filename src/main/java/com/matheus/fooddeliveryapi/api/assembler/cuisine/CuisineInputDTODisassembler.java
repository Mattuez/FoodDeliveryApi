package com.matheus.fooddeliveryapi.api.assembler.cuisine;

import com.matheus.fooddeliveryapi.api.model.cuisine.CuisineInputDTO;
import com.matheus.fooddeliveryapi.domain.model.Cuisine;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CuisineInputDTODisassembler {

    private ModelMapper modelMapper;

    public CuisineInputDTODisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Cuisine toDomainObject(CuisineInputDTO cuisineInputDTO) {
        return modelMapper.map(cuisineInputDTO, Cuisine.class);
    }

    public void copyToDomainObject(CuisineInputDTO cuisineInputDTO, Cuisine cuisine) {
        modelMapper.map(cuisineInputDTO, cuisine);
    }
}
