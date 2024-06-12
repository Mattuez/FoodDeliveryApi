package com.matheus.fooddeliveryapi.api.assembler.accessLevel;


import com.matheus.fooddeliveryapi.api.model.accessLevel.AccessLevelDto;
import com.matheus.fooddeliveryapi.domain.model.AccessLevel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class AccessLevelDtoAssembler {

    private ModelMapper modelMapper;

    public AccessLevelDtoAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AccessLevelDto toDto(AccessLevel source) {
        return modelMapper.map(source, AccessLevelDto.class);
    }

    public List<AccessLevelDto> toDtoCollection(Collection<AccessLevel> source) {
        return source.stream()
                .map(accessLevel -> toDto(accessLevel))
                .toList();
    }
}
