package com.matheus.fooddeliveryapi.api.assembler.city;

import com.matheus.fooddeliveryapi.api.model.city.CityInputDTO;
import com.matheus.fooddeliveryapi.domain.model.City;
import com.matheus.fooddeliveryapi.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CityInputDTODisassembler {

    private ModelMapper modelMapper;

    public CityInputDTODisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public City toDomainObject(CityInputDTO cityInputDTO) {
        return modelMapper.map(cityInputDTO, City.class);
    }

    public void copyToDomainObject(CityInputDTO cityInputDTO, City city) {
        city.setState(new State());

        modelMapper.map(cityInputDTO, city);
    }
}
