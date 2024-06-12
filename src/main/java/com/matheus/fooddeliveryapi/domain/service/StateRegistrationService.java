package com.matheus.fooddeliveryapi.domain.service;

import com.matheus.fooddeliveryapi.domain.exception.EntityBeingUsedException;
import com.matheus.fooddeliveryapi.domain.exception.StateNotFoundException;
import com.matheus.fooddeliveryapi.domain.model.State;
import com.matheus.fooddeliveryapi.domain.repository.StateRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StateRegistrationService {

    public static final String MSG_STATE_BEING_USED = "State with code %d is being used and can't be removed";

    private StateRepository stateRepository;

    public StateRegistrationService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public List<State> searchAll() {
        return stateRepository.findAll();
    }

    public State search(Long stateId) {
        return stateRepository.findById(stateId)
                .orElseThrow(() -> new StateNotFoundException(stateId));
    }

    @Transactional
    public State insert(State state) {
        return stateRepository.save(state);
    }

    @Transactional
    public void exclude(Long stateId) {
        try {
            stateRepository.deleteById(stateId);
            stateRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityBeingUsedException(
                    String.format(MSG_STATE_BEING_USED, stateId)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new StateNotFoundException(stateId);
        }
    }
}
