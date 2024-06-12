package com.matheus.fooddeliveryapi.domain.service;

import com.matheus.fooddeliveryapi.api.model.user.UserChangePasswordDto;
import com.matheus.fooddeliveryapi.domain.exception.BusinessException;
import com.matheus.fooddeliveryapi.domain.exception.EntityBeingUsedException;
import com.matheus.fooddeliveryapi.domain.exception.UserNotFoundException;
import com.matheus.fooddeliveryapi.domain.model.AccessLevel;
import com.matheus.fooddeliveryapi.domain.model.User;
import com.matheus.fooddeliveryapi.domain.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserRegistrationService {

    private UserRepository userRepository;
    private AccessLevelRegistrationService accessLevelService;

    public UserRegistrationService(UserRepository userRepository, AccessLevelRegistrationService accessLevelService) {
        this.userRepository = userRepository;
        this.accessLevelService = accessLevelService;
    }

    public List<User> searchAll() {
        return userRepository.findAll();
    }

    public User search(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional
    public User insert(User user) {
        userRepository.detach(user);

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent() && !existingUser.get().equals(user)) {
            throw new BusinessException("Already exits a user with the email %s".formatted(user.getEmail()));
        }

        return userRepository.save(user);
    }

    @Transactional
    public void exclude(Long userId) {
        try {
            userRepository.deleteById(userId);
            userRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityBeingUsedException("User", userId);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(userId);
        }
    }

    @Transactional
    public void alterPassword(Long userId, UserChangePasswordDto userChangePasswordDto) {
        User user = search(userId);

        if (!userChangePasswordDto.getCurrentPassword().equals(user.getPassword())) {
            throw new BusinessException("The current password entered does not match the user's password");
        }

        user.setPassword(userChangePasswordDto.getNewPassword());
    }

    @Transactional
    public void associateAccessLevel(Long userId, Long accessLevelId) {
        User user = search(userId);
        AccessLevel accessLevel = accessLevelService.search(accessLevelId);

        user.addAccessLevel(accessLevel);
    }

    @Transactional
    public void disassociateAccessLevel(Long userId, Long accessLevelId) {
        User user = search(userId);
        AccessLevel accessLevel = accessLevelService.search(accessLevelId);

        user.removeAccessLevel(accessLevel);
    }
}
