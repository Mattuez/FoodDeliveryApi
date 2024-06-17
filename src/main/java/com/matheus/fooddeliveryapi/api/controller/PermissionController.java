package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.permission.PermissionDtoAssembler;
import com.matheus.fooddeliveryapi.api.assembler.permission.PermissionDtoDisassembler;
import com.matheus.fooddeliveryapi.api.model.permission.PermissionDto;
import com.matheus.fooddeliveryapi.api.model.permission.PermissionInputDto;
import com.matheus.fooddeliveryapi.core.security.CheckSecurity;
import com.matheus.fooddeliveryapi.domain.model.Permission;
import com.matheus.fooddeliveryapi.domain.service.PermissionRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    private PermissionRegistrationService permissionRegistrationService;
    private PermissionDtoAssembler permissionDtoAssembler;
    private PermissionDtoDisassembler permissionDtoDisassembler;

    public PermissionController(PermissionRegistrationService permissionRegistrationService,
                                PermissionDtoAssembler permissionDtoAssembler,
                                PermissionDtoDisassembler permissionDtoDisassembler) {
        this.permissionRegistrationService = permissionRegistrationService;
        this.permissionDtoAssembler = permissionDtoAssembler;
        this.permissionDtoDisassembler = permissionDtoDisassembler;
    }

    @CheckSecurity.UserGroupAccessLevel.canView
    @GetMapping
    public List<PermissionDto> getAll() {
        return permissionDtoAssembler.toDtoCollection(permissionRegistrationService.searchAll());
    }

    @CheckSecurity.UserGroupAccessLevel.canView
    @GetMapping("/{permissionId}")
    public PermissionDto getById(@PathVariable("permissionId") Long permissionId) {
        return permissionDtoAssembler.toDto(permissionRegistrationService.search(permissionId));
    }

    @CheckSecurity.UserGroupAccessLevel.canAlter
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PermissionDto add(@RequestBody @Valid PermissionInputDto permissionInputDto) {
        Permission permission = permissionDtoDisassembler.toEntityObject(permissionInputDto);

        return permissionDtoAssembler.toDto(permissionRegistrationService.insert(permission));
    }

    @CheckSecurity.UserGroupAccessLevel.canAlter
    @PutMapping("/{permissionId}")
    public PermissionDto update(@PathVariable("permissionId") Long permissionId,
                                @RequestBody @Valid PermissionInputDto source) {
        Permission existingPermission = permissionRegistrationService.search(permissionId);

        permissionDtoDisassembler.copyToEntityObject(source, existingPermission);

        return permissionDtoAssembler.toDto(permissionRegistrationService.insert(existingPermission));
    }

    @CheckSecurity.UserGroupAccessLevel.canAlter
    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("permissionId") Long permissionId) {
        permissionRegistrationService.exclude(permissionId);
    }
}
