package com.matheus.fooddeliveryapi.api.assembler.user;

import com.matheus.fooddeliveryapi.api.model.user.UserAddInputDto;
import com.matheus.fooddeliveryapi.api.model.user.UserUpdateInputDto;
import com.matheus.fooddeliveryapi.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDtoDisassembler {

    private ModelMapper modelMapper;

    public UserDtoDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toEntityObject(UserAddInputDto source) {
        return modelMapper.map(source, User.class);
    }

    public User toEntityObject(UserUpdateInputDto source) {
        return modelMapper.map(source, User.class);
    }

    public void copyToEntityObject(UserAddInputDto source, User destination) {
        modelMapper.map(source, destination);
    }

    public void copyToEntityObject(UserUpdateInputDto source, User destination) {
        modelMapper.map(source, destination);
    }
}
