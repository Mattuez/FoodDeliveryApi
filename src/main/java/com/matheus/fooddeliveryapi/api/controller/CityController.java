package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.city.CityDTOAssembler;
import com.matheus.fooddeliveryapi.api.assembler.city.CityInputDTODisassembler;
import com.matheus.fooddeliveryapi.api.model.city.CityDTO;
import com.matheus.fooddeliveryapi.api.model.city.CityInputDTO;
import com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation.CityOpenApi;
import com.matheus.fooddeliveryapi.core.security.CheckSecurity;
import com.matheus.fooddeliveryapi.domain.exception.BusinessException;
import com.matheus.fooddeliveryapi.domain.exception.EntityNotFoundException;
import com.matheus.fooddeliveryapi.domain.exception.StateNotFoundException;
import com.matheus.fooddeliveryapi.domain.model.City;
import com.matheus.fooddeliveryapi.domain.service.CityRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController implements CityOpenApi {

    private CityRegistrationService cityRegistrationService;
    private CityDTOAssembler cityDTOAssembler;
    private CityInputDTODisassembler cityInputDTODisassembler;

    public CityController(CityRegistrationService cityRegistrationService,
                          CityDTOAssembler cityDTOAssembler,
                          CityInputDTODisassembler cityInputDTODisassembler) {
        this.cityRegistrationService = cityRegistrationService;
        this.cityDTOAssembler = cityDTOAssembler;
        this.cityInputDTODisassembler = cityInputDTODisassembler;
    }

    @Override
    @CheckSecurity.City.canView
    @GetMapping
    public List<CityDTO> getAll() {
        return cityDTOAssembler.toCollectionDTO(cityRegistrationService.searchAll());
    }

    @Override
    @CheckSecurity.City.canView
    @GetMapping("/{cityId}")
    public CityDTO getById(@PathVariable("cityId") Long cityId) {
        return cityDTOAssembler.toDTO(cityRegistrationService.search(cityId));
    }

    @Override
    @CheckSecurity.City.canManage
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CityDTO add(@RequestBody @Valid CityInputDTO cityInputDTO) {
        try {
            City city = cityInputDTODisassembler.toDomainObject(cityInputDTO);

            return cityDTOAssembler.toDTO(cityRegistrationService.insert(city));
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @CheckSecurity.City.canManage
    @PutMapping("/{cityId}")
    public CityDTO update(@RequestBody @Valid CityInputDTO cityToBeCopied, @PathVariable("cityId") Long cityId) {
        City existingCity = cityRegistrationService.search(cityId);

        cityInputDTODisassembler.copyToDomainObject(cityToBeCopied, existingCity);

        try {
            return cityDTOAssembler.toDTO(cityRegistrationService.insert(existingCity));
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @CheckSecurity.City.canManage
    @DeleteMapping("/{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("cityId") Long cityId) {
        cityRegistrationService.exclude(cityId);
    }
}
