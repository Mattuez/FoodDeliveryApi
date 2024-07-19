package com.matheus.fooddeliveryapi.api.assembler.cuisine;

import com.matheus.fooddeliveryapi.api.model.cuisine.CuisineDTO;
import com.matheus.fooddeliveryapi.domain.model.Cuisine;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CuisineDTOAssembler {

    private ModelMapper modelMapper;

    public CuisineDTOAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CuisineDTO toDTO(Cuisine cuisine) {
        return modelMapper.map(cuisine, CuisineDTO.class);
    }

    public List<CuisineDTO> toCollectionDTO(List<Cuisine> cuisines) {
        return cuisines.stream()
                .map(cuisine -> toDTO(cuisine))
                .toList();
    }
}
