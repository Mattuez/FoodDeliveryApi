package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.product.ProductDtoAssembler;
import com.matheus.fooddeliveryapi.api.assembler.product.ProductDtoDisassembler;
import com.matheus.fooddeliveryapi.api.model.products.ProductDto;
import com.matheus.fooddeliveryapi.api.model.products.ProductInputDto;
import com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation.RestaurantProductOpenApi;
import com.matheus.fooddeliveryapi.core.security.CheckSecurity;
import com.matheus.fooddeliveryapi.domain.model.Product;
import com.matheus.fooddeliveryapi.domain.model.Restaurant;
import com.matheus.fooddeliveryapi.domain.service.ProductRegistrationService;
import com.matheus.fooddeliveryapi.domain.service.RestaurantRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RestaurantProductsController implements RestaurantProductOpenApi {

    private ProductRegistrationService productRegistrationService;
    private RestaurantRegistrationService restaurantRegistrationService;
    private ProductDtoAssembler productDtoAssembler;
    private ProductDtoDisassembler productDtoDisassembler;


    public RestaurantProductsController(RestaurantRegistrationService restaurantRegistrationService,
                                        ProductDtoAssembler productDtoAssembler,
                                        ProductRegistrationService productRegistrationService,
                                        ProductDtoDisassembler productDtoDisassembler) {
        this.restaurantRegistrationService = restaurantRegistrationService;
        this.productDtoAssembler = productDtoAssembler;
        this.productRegistrationService = productRegistrationService;
        this.productDtoDisassembler = productDtoDisassembler;
    }

    @Override
    @CheckSecurity.Restaurant.canView
    @GetMapping
    public List<ProductDto> getAllByRestaurantId(@PathVariable("restaurantId") Long restaurantId,
                                                 @RequestParam(required = false) boolean includeInactive) {
        Restaurant restaurant = restaurantRegistrationService.search(restaurantId);



        return productDtoAssembler
                .toCollectionDto(productRegistrationService.searchByRestaurant(restaurant, includeInactive));
    }

    @Override
    @CheckSecurity.Restaurant.canView
    @GetMapping("/{productId}")
    public ProductDto getById(@PathVariable("restaurantId") Long restaurantId,
                              @PathVariable("productId") Long productId) {

        return productDtoAssembler.toDto(productRegistrationService.search(productId, restaurantId));
    }

    @Override
    @CheckSecurity.Restaurant.canManageOperation
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto add(@PathVariable("restaurantId") Long restaurantId,
                          @RequestBody @Valid ProductInputDto source) {

        Restaurant restaurant = restaurantRegistrationService.search(restaurantId);
        Product product = productDtoDisassembler.toEntityObject(source);
        product.setRestaurant(restaurant);

        return productDtoAssembler.toDto(productRegistrationService.insert(product));
    }

    @Override
    @CheckSecurity.Restaurant.canManageOperation
    @PutMapping("/{productId}")
    public ProductDto update(@PathVariable("restaurantId") Long restaurantId,
                             @PathVariable("productId") Long productId,
                             @RequestBody @Valid ProductInputDto source) {

        Product existingProduct = productRegistrationService.search(productId, restaurantId);

        productDtoDisassembler.copyToEntityObject(source, existingProduct);

        return productDtoAssembler.toDto(productRegistrationService.insert(existingProduct));
    }
}
