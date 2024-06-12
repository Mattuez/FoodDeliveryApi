package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.accessLevel.AccessLevelDtoAssembler;
import com.matheus.fooddeliveryapi.api.model.accessLevel.AccessLevelDto;
import com.matheus.fooddeliveryapi.domain.model.User;
import com.matheus.fooddeliveryapi.domain.service.AccessLevelRegistrationService;
import com.matheus.fooddeliveryapi.domain.service.UserRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/{userId}/accessLevel")
public class UserAccessLevelController {

    private UserRegistrationService userRegistrationService;
    private AccessLevelRegistrationService accessLevelRegistrationService;
    private AccessLevelDtoAssembler accessLevelDtoAssembler;

    public UserAccessLevelController(UserRegistrationService userRegistrationService,
                                     AccessLevelRegistrationService accessLevelRegistrationService,
                                     AccessLevelDtoAssembler accessLevelDtoAssembler) {
        this.userRegistrationService = userRegistrationService;
        this.accessLevelRegistrationService = accessLevelRegistrationService;
        this.accessLevelDtoAssembler = accessLevelDtoAssembler;
    }

    @GetMapping
    public List<AccessLevelDto> getAllByUserId(@PathVariable("userId") Long userId) {
        User user = userRegistrationService.search(userId);

        return accessLevelDtoAssembler.toDtoCollection(user.getAccessLevel());
    }

    @PutMapping("/{accessLevelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associate(@PathVariable("userId") Long userId, @PathVariable("accessLevelId") Long accessLevelId) {
        userRegistrationService.associateAccessLevel(userId, accessLevelId);
    }

    @DeleteMapping("/{accessLevelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable("userId") Long userId, @PathVariable("accessLevelId") Long accessLevelId) {
        userRegistrationService.disassociateAccessLevel(userId, accessLevelId);
    }
}
