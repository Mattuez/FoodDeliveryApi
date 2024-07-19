package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.restaurant.RestaurantDTOAssembler;
import com.matheus.fooddeliveryapi.api.assembler.restaurant.RestaurantInputDTODisassembler;
import com.matheus.fooddeliveryapi.api.model.restaurant.RestaurantDTO;
import com.matheus.fooddeliveryapi.api.model.restaurant.RestaurantInputDTO;
import com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation.RestaurantOpenApi;
import com.matheus.fooddeliveryapi.core.security.CheckSecurity;
import com.matheus.fooddeliveryapi.domain.exception.BusinessException;
import com.matheus.fooddeliveryapi.domain.exception.CityNotFoundException;
import com.matheus.fooddeliveryapi.domain.exception.CuisineNotFoundException;
import com.matheus.fooddeliveryapi.domain.filter.RestaurantFilter;
import com.matheus.fooddeliveryapi.domain.model.Restaurant;
import com.matheus.fooddeliveryapi.domain.service.RestaurantRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController implements RestaurantOpenApi {

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
    public List<RestaurantDTO> getAll(RestaurantFilter restaurantFilter) {
        return restaurantDTOAssembler
                .toCollectionDTO(restaurantRegistrationService.searchAll(restaurantFilter));
    }

    @GetMapping("/{restaurantId}")
    public RestaurantDTO getById(@PathVariable("restaurantId") Long restaurantId) {
        return restaurantDTOAssembler
                .toDTO(restaurantRegistrationService.search(restaurantId));
    }

    @CheckSecurity.Restaurant.canManageAdministration
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

    @CheckSecurity.Restaurant.canManageAdministration
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

    /*
    Just to have some reference to how to patch:

    @PatchMapping("/{restaurantId}")
    public Restaurant partialUpdate(@PathVariable("restaurantId") Long restaurantId, @RequestBody Map<String, Object> updatedFields,
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
     */

    @CheckSecurity.Restaurant.canManageAdministration
    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("restaurantId") Long restaurantId) {
        restaurantRegistrationService.exclude(restaurantId);
    }

    @CheckSecurity.Restaurant.canManageAdministration
    @PutMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@PathVariable("restaurantId") Long restaurantId) {
        restaurantRegistrationService.activate(restaurantId);
    }

    @CheckSecurity.Restaurant.canManageAdministration
    @DeleteMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable("restaurantId") Long restaurantId) {
        restaurantRegistrationService.deactivate(restaurantId);
    }

    @CheckSecurity.Restaurant.canManageAdministration
    @PutMapping("/{restaurantId}/verified")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verify(@PathVariable("restaurantId") Long restaurantId) {
        restaurantRegistrationService.verifyRestaurant(restaurantId);
    }

    @CheckSecurity.Restaurant.canManageAdministration
    @DeleteMapping("/{restaurantId}/verified")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unverify(@PathVariable("restaurantId") Long restaurantId) {
        restaurantRegistrationService.unverifyRestaurant(restaurantId);
    }

    @CheckSecurity.Restaurant.canManageAdministration
    @PutMapping("/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateCollection(@RequestBody List<Long> restaurantIds) {
        restaurantRegistrationService.activate(restaurantIds);
    }

    @CheckSecurity.Restaurant.canManageAdministration
    @DeleteMapping("/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateCollection(@RequestBody List<Long> restaurantIds) {
        restaurantRegistrationService.deactivate(restaurantIds);
    }

    @CheckSecurity.Restaurant.canManageOperation
    @PutMapping("/{restaurantId}/opened")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void opened(@PathVariable("restaurantId") Long restaurantId) {
        restaurantRegistrationService.openRestaurant(restaurantId);
    }

    @CheckSecurity.Restaurant.canManageOperation
    @PutMapping("/{restaurantId}/closed")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closed(@PathVariable("restaurantId") Long restaurantId) {
        restaurantRegistrationService.closeRestaurant(restaurantId);
    }
}
