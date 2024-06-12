package com.matheus.fooddeliveryapi.domain.service;

import com.matheus.fooddeliveryapi.domain.exception.EntityBeingUsedException;
import com.matheus.fooddeliveryapi.domain.exception.PermissionNotFoundException;
import com.matheus.fooddeliveryapi.domain.model.Permission;
import com.matheus.fooddeliveryapi.domain.repository.PermissionRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionRegistrationService {

    public static final String MSG_PERMISSION_BEING_USED = "Permission with code %d is being used and can't be removed";

    private PermissionRepository permissionRepository;

    public PermissionRegistrationService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<Permission> searchAll() {
        return permissionRepository.findAll();
    }

    public Permission search(Long permissionId) {
        return permissionRepository.findById(permissionId)
                .orElseThrow(() -> new PermissionNotFoundException(permissionId));
    }

    @Transactional
    public Permission insert(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Transactional
    public void exclude(Long permissionId) {
        try {
            permissionRepository.deleteById(permissionId);
            permissionRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PermissionNotFoundException(permissionId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityBeingUsedException(
                    String.format(MSG_PERMISSION_BEING_USED, permissionId)
            );
        }
    }
}
