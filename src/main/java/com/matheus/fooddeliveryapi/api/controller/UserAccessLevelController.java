package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.accessLevel.AccessLevelDtoAssembler;
import com.matheus.fooddeliveryapi.api.model.accessLevel.AccessLevelDto;
import com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation.UserAccessLevelOpenApi;
import com.matheus.fooddeliveryapi.core.security.CheckSecurity;
import com.matheus.fooddeliveryapi.domain.model.User;
import com.matheus.fooddeliveryapi.domain.service.AccessLevelRegistrationService;
import com.matheus.fooddeliveryapi.domain.service.UserRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/{userId}/accessLevel")
public class UserAccessLevelController implements UserAccessLevelOpenApi {

    private UserRegistrationService userRegistrationService;
    private AccessLevelDtoAssembler accessLevelDtoAssembler;

    public UserAccessLevelController(UserRegistrationService userRegistrationService,
                                     AccessLevelDtoAssembler accessLevelDtoAssembler) {
        this.userRegistrationService = userRegistrationService;
        this.accessLevelDtoAssembler = accessLevelDtoAssembler;
    }

    @CheckSecurity.UserGroupAccessLevel.canView
    @GetMapping
    public List<AccessLevelDto> getAllByUserId(@PathVariable("userId") Long userId) {
        User user = userRegistrationService.search(userId);

        return accessLevelDtoAssembler.toDtoCollection(user.getAccessLevel());
    }

    @CheckSecurity.UserGroupAccessLevel.canAlter
    @PutMapping("/{accessLevelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associate(@PathVariable("userId") Long userId, @PathVariable("accessLevelId") Long accessLevelId) {
        userRegistrationService.associateAccessLevel(userId, accessLevelId);
    }

    @CheckSecurity.UserGroupAccessLevel.canAlter
    @DeleteMapping("/{accessLevelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable("userId") Long userId, @PathVariable("accessLevelId") Long accessLevelId) {
        userRegistrationService.disassociateAccessLevel(userId, accessLevelId);
    }
}
