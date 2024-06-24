package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.permission.PermissionDtoAssembler;
import com.matheus.fooddeliveryapi.api.assembler.permission.PermissionDtoDisassembler;
import com.matheus.fooddeliveryapi.api.model.permission.PermissionDto;
import com.matheus.fooddeliveryapi.api.model.permission.PermissionInputDto;
import com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation.PermissionOpenApi;
import com.matheus.fooddeliveryapi.core.security.CheckSecurity;
import com.matheus.fooddeliveryapi.domain.model.Permission;
import com.matheus.fooddeliveryapi.domain.service.PermissionRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController implements PermissionOpenApi {

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

    @Override
    @CheckSecurity.UserGroupAccessLevel.canView
    @GetMapping
    public List<PermissionDto> getAll() {
        return permissionDtoAssembler.toDtoCollection(permissionRegistrationService.searchAll());
    }

    @Override
    @CheckSecurity.UserGroupAccessLevel.canView
    @GetMapping("/{permissionId}")
    public PermissionDto getById(@PathVariable("permissionId") Long permissionId) {
        return permissionDtoAssembler.toDto(permissionRegistrationService.search(permissionId));
    }
}
