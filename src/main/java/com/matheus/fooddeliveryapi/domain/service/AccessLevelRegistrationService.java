package com.matheus.fooddeliveryapi.domain.service;

import com.matheus.fooddeliveryapi.domain.exception.AccessLevelNotFoundException;
import com.matheus.fooddeliveryapi.domain.exception.EntityBeingUsedException;
import com.matheus.fooddeliveryapi.domain.model.AccessLevel;
import com.matheus.fooddeliveryapi.domain.model.Permission;
import com.matheus.fooddeliveryapi.domain.repository.AccessLevelRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccessLevelRegistrationService {

    private AccessLevelRepository accessLevelRepository;
    private PermissionRegistrationService permissionRegistrationService;

    public AccessLevelRegistrationService(AccessLevelRepository accessLevelRepository, PermissionRegistrationService permissionRegistrationService) {
        this.accessLevelRepository = accessLevelRepository;
        this.permissionRegistrationService = permissionRegistrationService;
    }

    public List<AccessLevel> searchAll() {
        return accessLevelRepository.findAll();
    }

    public AccessLevel search(Long accessLevelId) {
        return accessLevelRepository.findById(accessLevelId)
                .orElseThrow(() -> new AccessLevelNotFoundException(accessLevelId));
    }

    @Transactional
    public AccessLevel insert(AccessLevel accessLevel) {
        return accessLevelRepository.save(accessLevel);
    }

    @Transactional
    public void exclude(Long accessLevelId) {
        try {
            accessLevelRepository.deleteById(accessLevelId);
            accessLevelRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new AccessLevelNotFoundException(accessLevelId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityBeingUsedException("AccessLevel", accessLevelId);
        }
    }

    @Transactional
    public void associatePermission(Long accessLevelId, Long permissionId) {
        AccessLevel accessLevel = search(accessLevelId);
        Permission permission = permissionRegistrationService.search(permissionId);

        accessLevel.addPermission(permission);
    }

    @Transactional
    public void disassociatePermission(Long accessLevelId, Long permissionId) {
        AccessLevel accessLevel = search(accessLevelId);
        Permission permission = permissionRegistrationService.search(permissionId);

        accessLevel.removePermission(permission);
    }
}
