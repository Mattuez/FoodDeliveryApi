package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.user.UserDtoAssembler;
import com.matheus.fooddeliveryapi.api.assembler.user.UserDtoDisassembler;
import com.matheus.fooddeliveryapi.api.model.user.UserAddInputDto;
import com.matheus.fooddeliveryapi.api.model.user.UserChangePasswordDto;
import com.matheus.fooddeliveryapi.api.model.user.UserDto;
import com.matheus.fooddeliveryapi.api.model.user.UserUpdateInputDto;
import com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation.UserOpenApi;
import com.matheus.fooddeliveryapi.core.security.CheckSecurity;
import com.matheus.fooddeliveryapi.domain.exception.AccessLevelNotFoundException;
import com.matheus.fooddeliveryapi.domain.exception.BusinessException;
import com.matheus.fooddeliveryapi.domain.model.User;
import com.matheus.fooddeliveryapi.domain.service.UserRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController implements UserOpenApi {

    private UserRegistrationService userService;
    private UserDtoAssembler userDtoAssembler;
    private UserDtoDisassembler userDtoDisassembler;

    public UserController(UserRegistrationService userService, UserDtoAssembler userDtoAssembler, UserDtoDisassembler userDtoDisassembler) {
        this.userService = userService;
        this.userDtoAssembler = userDtoAssembler;
        this.userDtoDisassembler = userDtoDisassembler;
    }

    @CheckSecurity.UserGroupAccessLevel.canView
    @GetMapping
    public List<UserDto> getAll(){
        return userDtoAssembler.toDtoCollection(userService.searchAll());
    }

    @CheckSecurity.UserGroupAccessLevel.canView
    @GetMapping("/{userId}")
    public UserDto getById(@PathVariable("userId") Long userId) {
        return userDtoAssembler.toDto(userService.search(userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto add(@RequestBody @Valid UserAddInputDto userInputDto) {
        try {
            User user = userDtoDisassembler.toEntityObject(userInputDto);

            return userDtoAssembler.toDto(userService.insert(user));
        } catch (AccessLevelNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @CheckSecurity.UserGroupAccessLevel.canAlterUser
    @PutMapping("/{userId}")
    public UserDto update(@PathVariable("userId") Long userId,
                          @RequestBody @Valid UserUpdateInputDto source) {
        try {
            User existingUser = userService.search(userId);

            userDtoDisassembler.copyToEntityObject(source, existingUser);

            return userDtoAssembler.toDto(userService.insert(existingUser));
        } catch (AccessLevelNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @CheckSecurity.UserGroupAccessLevel.canAlter
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("userId") Long userId) {
        userService.exclude(userId);
    }

    @CheckSecurity.UserGroupAccessLevel.canAlterOwnPassword
    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable("userId") Long userId,
                               @RequestBody @Valid UserChangePasswordDto userChangePasswordDto) {
        userService.alterPassword(userId, userChangePasswordDto);
    }
}
