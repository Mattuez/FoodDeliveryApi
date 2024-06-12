package com.matheus.fooddeliveryapi.api.assembler.city;

import com.matheus.fooddeliveryapi.api.model.city.CityDTO;
import com.matheus.fooddeliveryapi.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CityDTOAssembler {

    private ModelMapper modelMapper;

    public CityDTOAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CityDTO toDTO(City city) {
        return modelMapper.map(city, CityDTO.class);
    }

    public List<CityDTO> toCollectionDTO(List<City> cities) {
        return cities.stream()
                .map(city -> modelMapper.map(city, CityDTO.class))
                .toList();
    }
}
