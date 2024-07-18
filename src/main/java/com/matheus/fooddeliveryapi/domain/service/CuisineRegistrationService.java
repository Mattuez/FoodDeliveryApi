package com.matheus.fooddeliveryapi.domain.service;

import com.matheus.fooddeliveryapi.domain.exception.CuisineNotFoundException;
import com.matheus.fooddeliveryapi.domain.exception.EntityBeingUsedException;
import com.matheus.fooddeliveryapi.domain.model.Cuisine;
import com.matheus.fooddeliveryapi.domain.repository.CuisineRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CuisineRegistrationService {

    public static final String MSG_CUISINE_BEING_USED = "Cuisine with code %d is being used and can't be removed";
    private static final String WHITE = "FFFFFF";

    private CuisineRepository cuisineRepository;

    public CuisineRegistrationService(CuisineRepository cuisineRepository) {
        this.cuisineRepository = cuisineRepository;
    }

    public List<Cuisine> searchAll() {
        return cuisineRepository.findAll();
    }

    public Page<Cuisine> searchAll(Pageable pageable) {
        return cuisineRepository.findAll(pageable);
    }

    public Cuisine search(Long cuisineId) {
        return cuisineRepository.findById(cuisineId)
                .orElseThrow(() -> new CuisineNotFoundException(cuisineId));
    }

    @Transactional
    public Cuisine insert(Cuisine cuisine) {
        if (cuisine.getColor() == null) {
            cuisine.setColor(WHITE);
        }

        return cuisineRepository.save(cuisine);
    }

    @Transactional
    public void exclude(Long cuisineId) {
        try {
            cuisineRepository.deleteById(cuisineId);
            cuisineRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityBeingUsedException(
                    String.format(MSG_CUISINE_BEING_USED, cuisineId)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new CuisineNotFoundException(cuisineId);
        }
    }
}
