package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.cuisine.CuisineDTOAssembler;
import com.matheus.fooddeliveryapi.api.assembler.cuisine.CuisineInputDTODisassembler;
import com.matheus.fooddeliveryapi.api.model.cuisine.CuisineDTO;
import com.matheus.fooddeliveryapi.api.model.cuisine.CuisineInputDTO;
import com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation.CuisineOpenApi;
import com.matheus.fooddeliveryapi.core.security.CheckSecurity;
import com.matheus.fooddeliveryapi.domain.model.Cuisine;
import com.matheus.fooddeliveryapi.domain.service.CuisineRegistrationService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/cuisines")
public class CuisineController implements CuisineOpenApi {
    private CuisineRegistrationService cuisineRegistrationService;
    private CuisineDTOAssembler cuisineDTOAssembler;
    private CuisineInputDTODisassembler cuisineInputDTODisassembler;

    public CuisineController(CuisineRegistrationService cuisineRegistrationService,
                             CuisineDTOAssembler cuisineDTOAssembler,
                             CuisineInputDTODisassembler cuisineInputDTODisassembler) {
        this.cuisineRegistrationService = cuisineRegistrationService;
        this.cuisineDTOAssembler = cuisineDTOAssembler;
        this.cuisineInputDTODisassembler = cuisineInputDTODisassembler;
    }

    @GetMapping
    public ResponseEntity<List<CuisineDTO>> getAll() {
        List<CuisineDTO> cuisines = cuisineDTOAssembler.toCollectionDTO(cuisineRegistrationService.searchAll());

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
                .body(cuisines);
    }

    /*
    @GetMapping
    public Page<CuisineDTO> getAll(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cuisine> cuisinePage = cuisineRegistrationService.searchAll(pageable);

        List<CuisineDTO> cuisineDTOList = cuisineDTOAssembler.toCollectionDTO(cuisinePage.getContent());

        return new PageImpl<>(cuisineDTOList, pageable, cuisinePage.getTotalElements());
    }
    */

    @GetMapping("/{cuisineId}")
    public CuisineDTO getById(@PathVariable("cuisineId") Long cuisineId) {
        return cuisineDTOAssembler.toDTO(
                cuisineRegistrationService.search(cuisineId)
        );
    }

    @CheckSecurity.Cuisines.CanEdit
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CuisineDTO add(@RequestBody @Valid CuisineInputDTO cuisineInputDTO) {
        Cuisine cuisine = cuisineInputDTODisassembler.toDomainObject(cuisineInputDTO);

        return cuisineDTOAssembler.toDTO(cuisineRegistrationService.insert(cuisine));
    }

    @CheckSecurity.Cuisines.CanEdit
    @PutMapping("/{cuisineId}")
    public CuisineDTO update(@RequestBody @Valid CuisineInputDTO cuisineToBeCopied, @PathVariable("cuisineId") Long cuisineId) {
        Cuisine existingCuisine = cuisineRegistrationService.search(cuisineId);

        cuisineInputDTODisassembler.copyToDomainObject(cuisineToBeCopied, existingCuisine);

        return cuisineDTOAssembler
                .toDTO(cuisineRegistrationService.insert(existingCuisine));
    }

    @CheckSecurity.Cuisines.CanEdit
    @DeleteMapping("/{cuisineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("cuisineId") Long cuisineId) {
        cuisineRegistrationService.exclude(cuisineId);
    }
}
