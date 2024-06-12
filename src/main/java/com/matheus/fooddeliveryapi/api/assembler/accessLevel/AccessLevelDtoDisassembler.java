package com.matheus.fooddeliveryapi.api.assembler.accessLevel;

import com.matheus.fooddeliveryapi.api.model.accessLevel.AccessLevelInputDto;
import com.matheus.fooddeliveryapi.domain.model.AccessLevel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccessLevelDtoDisassembler {

    private ModelMapper modelMapper;

    public AccessLevelDtoDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AccessLevel toEntityObject(AccessLevelInputDto source) {
        return modelMapper.map(source, AccessLevel.class);
    }

    public void copyToEntityObject(AccessLevelInputDto source, AccessLevel destination) {
        modelMapper.map(source, destination);
    }
}
