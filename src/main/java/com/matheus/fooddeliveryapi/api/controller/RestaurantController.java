package com.matheus.fooddeliveryapi.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheus.fooddeliveryapi.api.assembler.restaurant.RestaurantDTOAssembler;
import com.matheus.fooddeliveryapi.api.assembler.restaurant.RestaurantInputDTODisassembler;
import com.matheus.fooddeliveryapi.api.model.restaurant.RestaurantDTO;
import com.matheus.fooddeliveryapi.api.model.restaurant.RestaurantInputDTO;
import com.matheus.fooddeliveryapi.core.validation.ValidationException;
import com.matheus.fooddeliveryapi.domain.exception.BusinessException;
import com.matheus.fooddeliveryapi.domain.exception.CityNotFoundException;
import com.matheus.fooddeliveryapi.domain.exception.CuisineNotFoundException;
import com.matheus.fooddeliveryapi.domain.model.Restaurant;
import com.matheus.fooddeliveryapi.domain.service.RestaurantRegistrationService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private RestaurantRegistrationService restaurantRegistrationService;
    private SmartValidator smartValidator;
    private RestaurantDTOAssembler restaurantDTOAssembler;
    private RestaurantInputDTODisassembler restaurantInputDTODisassembler;

    public RestaurantController(RestaurantRegistrationService restaurantRegistrationService,
                                SmartValidator smartValidator,
                                RestaurantDTOAssembler restaurantDTOAssembler,
                                RestaurantInputDTODisassembler restaurantInputDTODisassembler) {
        this.restaurantRegistrationService = restaurantRegistrationService;
        this.smartValidator = smartValidator;
        this.restaurantDTOAssembler = restaurantDTOAssembler;
        this.restaurantInputDTODisassembler = restaurantInputDTODisassembler;
    }

    @GetMapping
    public List<RestaurantDTO> getAll() {
        return restaurantDTOAssembler.toCollectionDTO(restaurantRegistrationService.searchAll());
    }

    @GetMapping("/{restaurantId}")
    public RestaurantDTO getById(@PathVariable("restaurantId") Long restaurantId) {
        return restaurantDTOAssembler
                .toDTO(restaurantRegistrationService.search(restaurantId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantDTO add(@RequestBody @Valid RestaurantInputDTO restaurantInputDTO) {
        try {
            Restaurant restaurant = restaurantInputDTODisassembler.toDomainObject(restaurantInputDTO);

            return restaurantDTOAssembler.toDTO(restaurantRegistrationService.insert(restaurant));
        } catch (CuisineNotFoundException | CityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{restaurantId}")
    public RestaurantDTO update(@RequestBody @Valid RestaurantInputDTO restaurantToBeCopied, @PathVariable("restaurantId") Long restaurantId) {
        Restaurant existingRestaurant = restaurantRegistrationService.search(restaurantId);

        restaurantInputDTODisassembler.copyToDomainObject(restaurantToBeCopied, existingRestaurant);

        try {
            return restaurantDTOAssembler.toDTO(restaurantRegistrationService.insert(existingRestaurant));
        } catch (CuisineNotFoundException | CityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PatchMapping("/{restaurantId}")
    public Restaurant parcialUpdate(@PathVariable("restaurantId") Long restaurantId, @RequestBody Map<String, Object> updatedFields,
            HttpServletRequest request) {
        Restaurant existingRestaurant = restaurantRegistrationService.search(restaurantId);

        merge(updatedFields, existingRestaurant, request);
        
        validate(existingRestaurant, "restaurant");

        return restaurantRegistrationService.insert(existingRestaurant);
    }

    private void validate(Restaurant restaurant, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurant, objectName);

        smartValidator.validate(restaurant, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
    }

    private void merge(Map<String, Object> updatedFields, Restaurant restaurantToUpdate, HttpServletRequest request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurant restaurantContainingUpdatedFields = objectMapper.convertValue(
                    updatedFields, Restaurant.class
            );

            updatedFields.forEach((propertyName, propertyValue) -> {
                Field field = ReflectionUtils.findField(Restaurant.class, propertyName);
                field.setAccessible(true);

                Object newFieldValue = ReflectionUtils.getField(field, restaurantContainingUpdatedFields);
                ReflectionUtils.setField(field, restaurantToUpdate, newFieldValue);
            });
        }
        catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            ServletServerHttpRequest serverHttpRequest= new ServletServerHttpRequest(request);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }

    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("restaurantId") Long restaurantId) {
        restaurantRegistrationService.exclude(restaurantId);
    }

    @PutMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@PathVariable("restaurantId") Long restaurantId) {
        restaurantRegistrationService.activate(restaurantId);
    }

    @DeleteMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable("restaurantId") Long restaurantId) {
        restaurantRegistrationService.deactivate(restaurantId);
    }

    @PutMapping("/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateCollection(@RequestBody List<Long> restaurantIds) {
        restaurantRegistrationService.activate(restaurantIds);
    }

    @DeleteMapping("/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateCollection(@RequestBody List<Long> restaurantIds) {
        restaurantRegistrationService.deactivate(restaurantIds);
    }

    @PutMapping("/{restaurantId}/opened")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void opened(@PathVariable("restaurantId") Long restaurantId) {
        restaurantRegistrationService.openRestaurant(restaurantId);
    }

    @PutMapping("/{restaurantId}/closed")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closed(@PathVariable("restaurantId") Long restaurantId) {
        restaurantRegistrationService.closeRestaurant(restaurantId);
    }
}
