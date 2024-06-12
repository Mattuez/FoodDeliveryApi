package com.matheus.fooddeliveryapi.domain.service;

import com.matheus.fooddeliveryapi.domain.exception.CityNotFoundException;
import com.matheus.fooddeliveryapi.domain.exception.EntityBeingUsedException;
import com.matheus.fooddeliveryapi.domain.model.City;
import com.matheus.fooddeliveryapi.domain.model.State;
import com.matheus.fooddeliveryapi.domain.repository.CityRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityRegistrationService {

    public static final String MSG_CITY_BEING_USED = "City with code %d is being used and can't be removed";
    private CityRepository cityRepository;
    private StateRegistrationService stateRegistrationService;

    public CityRegistrationService(CityRepository cityRepository, StateRegistrationService stateRegistrationService) {
        this.cityRepository = cityRepository;
        this.stateRegistrationService = stateRegistrationService;
    }

    public List<City> searchAll() {
        return cityRepository.findAll();
    }

    public City search(Long cityId) {
        return cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
    }

    @Transactional
    public City insert(City city) {
        Long stateId = city.getState().getId();
        State state = stateRegistrationService.search(stateId);

        city.setState(state);

        return cityRepository.save(city);
    }

    @Transactional
    public void exclude(Long cityId) {
        try {
            cityRepository.deleteById(cityId);
            cityRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(cityId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityBeingUsedException(
                    String.format(MSG_CITY_BEING_USED, cityId)
            );
        }
    }
}
