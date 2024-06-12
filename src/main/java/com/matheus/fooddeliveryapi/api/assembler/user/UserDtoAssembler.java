package com.matheus.fooddeliveryapi.api.assembler.user;

import com.matheus.fooddeliveryapi.api.model.user.UserDto;
import com.matheus.fooddeliveryapi.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class UserDtoAssembler {

    private ModelMapper modelMapper;

    public UserDtoAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto toDto(User source) {
        return modelMapper.map(source, UserDto.class);
    }

    public List<UserDto> toDtoCollection(Collection<User> source) {
        return source.stream()
                .map(user -> toDto(user))
                .toList();
    }
}
