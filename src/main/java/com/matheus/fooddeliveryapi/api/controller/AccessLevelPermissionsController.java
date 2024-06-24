package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.permission.PermissionDtoAssembler;
import com.matheus.fooddeliveryapi.api.model.permission.PermissionDto;
import com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation.AccessLevelPermissionsOpenApi;
import com.matheus.fooddeliveryapi.core.security.CheckSecurity;
import com.matheus.fooddeliveryapi.domain.model.AccessLevel;
import com.matheus.fooddeliveryapi.domain.service.AccessLevelRegistrationService;
import com.matheus.fooddeliveryapi.domain.service.PermissionRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accessLevel/{accessLevelId}/permissions")
public class AccessLevelPermissionsController implements AccessLevelPermissionsOpenApi {

    public AccessLevelRegistrationService accessLevelRegistrationService;
    public PermissionRegistrationService permissionRegistrationService;
    public PermissionDtoAssembler permissionDtoAssembler;

    public AccessLevelPermissionsController(AccessLevelRegistrationService accessLevelRegistrationService,
                                            PermissionDtoAssembler permissionDtoAssembler,
                                            PermissionRegistrationService permissionRegistrationService) {

        this.accessLevelRegistrationService = accessLevelRegistrationService;
        this.permissionDtoAssembler = permissionDtoAssembler;
        this.permissionRegistrationService = permissionRegistrationService;
    }

    @Override
    @CheckSecurity.UserGroupAccessLevel.canView
    @GetMapping
    public List<PermissionDto> getAllByAccessLevelId(@PathVariable("accessLevelId") Long accessLevelId) {
        AccessLevel accessLevel = accessLevelRegistrationService.search(accessLevelId);

        return permissionDtoAssembler.toDtoCollection(accessLevel.getPermissions());
    }

    @Override
    @CheckSecurity.UserGroupAccessLevel.canAlter
    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associate(@PathVariable("accessLevelId") Long accessLevelId,
                          @PathVariable("permissionId") Long permissionId) {
        accessLevelRegistrationService.associatePermission(accessLevelId, permissionId);
    }

    @Override
    @CheckSecurity.UserGroupAccessLevel.canAlter
    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable("accessLevelId") Long accessLevelId,
                          @PathVariable("permissionId") Long permissionId) {
        accessLevelRegistrationService.disassociatePermission(accessLevelId, permissionId);
    }
}
