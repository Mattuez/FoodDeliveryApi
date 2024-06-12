package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.accessLevel.AccessLevelDtoAssembler;
import com.matheus.fooddeliveryapi.api.assembler.accessLevel.AccessLevelDtoDisassembler;
import com.matheus.fooddeliveryapi.api.model.accessLevel.AccessLevelDto;
import com.matheus.fooddeliveryapi.api.model.accessLevel.AccessLevelInputDto;
import com.matheus.fooddeliveryapi.domain.exception.BusinessException;
import com.matheus.fooddeliveryapi.domain.exception.PermissionNotFoundException;
import com.matheus.fooddeliveryapi.domain.model.AccessLevel;
import com.matheus.fooddeliveryapi.domain.service.AccessLevelRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/accessLevel")
public class AccessLevelController {

    private AccessLevelRegistrationService accessLevelRegistrationService;
    private AccessLevelDtoAssembler accessLevelDtoAssembler;
    private AccessLevelDtoDisassembler accessLevelDtoDisassembler;


    public AccessLevelController(AccessLevelRegistrationService accessLevelRegistrationService,
                                 AccessLevelDtoAssembler accessLevelDtoAssembler,
                                 AccessLevelDtoDisassembler accessLevelDtoDisassembler) {
        this.accessLevelRegistrationService = accessLevelRegistrationService;
        this.accessLevelDtoAssembler = accessLevelDtoAssembler;
        this.accessLevelDtoDisassembler = accessLevelDtoDisassembler;
    }

    @GetMapping
    public List<AccessLevelDto> getAll() {
        return accessLevelDtoAssembler.toDtoCollection(accessLevelRegistrationService.searchAll());
    }

    @GetMapping("/{accessLevelId}")
    public AccessLevelDto getById(@PathVariable("accessLevelId") Long accessLevelId) {
        return accessLevelDtoAssembler.toDto(accessLevelRegistrationService.search(accessLevelId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccessLevelDto add(@RequestBody @Valid AccessLevelInputDto accessLevelInputDto) {
        try {
            AccessLevel accessLevel = accessLevelDtoDisassembler.toEntityObject(accessLevelInputDto);

            return accessLevelDtoAssembler.toDto(accessLevelRegistrationService.insert(accessLevel));
        } catch (PermissionNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{accessLevelId}")
    public AccessLevelDto update(@PathVariable("accessLevelId") Long accessLevelId,
                              @RequestBody @Valid AccessLevelInputDto source) {
        try {
            AccessLevel existingAccessLevel = accessLevelRegistrationService.search(accessLevelId);

            accessLevelDtoDisassembler.copyToEntityObject(source, existingAccessLevel);

            return accessLevelDtoAssembler.toDto(accessLevelRegistrationService.insert(existingAccessLevel));
        } catch (PermissionNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @DeleteMapping("/{accessLevelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("accessLevelId") Long accessLevelId) {
        accessLevelRegistrationService.exclude(accessLevelId);
    }
}
