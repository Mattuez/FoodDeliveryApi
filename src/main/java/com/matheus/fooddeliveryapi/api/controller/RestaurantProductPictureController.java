package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.productPicture.ProductPictureDtoAssembler;
import com.matheus.fooddeliveryapi.api.model.products.picture.ProductPictureDto;
import com.matheus.fooddeliveryapi.api.model.products.picture.ProductPictureInputDto;
import com.matheus.fooddeliveryapi.core.security.CheckSecurity;
import com.matheus.fooddeliveryapi.domain.exception.EntityNotFoundException;
import com.matheus.fooddeliveryapi.domain.model.Product;
import com.matheus.fooddeliveryapi.domain.model.ProductPicture;
import com.matheus.fooddeliveryapi.domain.service.CatalogProductPictureService;
import com.matheus.fooddeliveryapi.domain.service.PictureStorageService;
import com.matheus.fooddeliveryapi.domain.service.ProductRegistrationService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/picture")
public class RestaurantProductPictureController {

    private CatalogProductPictureService catalogProductPictureService;
    private ProductRegistrationService productRegistrationService;
    private ProductPictureDtoAssembler productPictureDtoAssembler;
    private PictureStorageService pictureStorageService;

    public RestaurantProductPictureController(CatalogProductPictureService catalogProductPictureService,
                                              ProductRegistrationService productRegistrationService,
                                              ProductPictureDtoAssembler productPictureDtoAssembler,
                                              PictureStorageService pictureStorageService) {
        this.catalogProductPictureService = catalogProductPictureService;
        this.productRegistrationService = productRegistrationService;
        this.productPictureDtoAssembler = productPictureDtoAssembler;
        this.pictureStorageService = pictureStorageService;
    }

    @CheckSecurity.Restaurant.canView
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductPictureDto getPictureData(@PathVariable("restaurantId") Long restaurantId,
                                 @PathVariable("productId") Long productId) {
        return productPictureDtoAssembler.toDto(catalogProductPictureService.search(productId, restaurantId));
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> getPicture(@PathVariable("restaurantId") Long restaurantId,
                                                          @PathVariable("productId") Long productId,
                                                          @RequestHeader(name = "accept") String acceptedMediaType)
            throws HttpMediaTypeNotAcceptableException{

        try {
            ProductPicture picture = catalogProductPictureService.search(productId, restaurantId);
            InputStream inputStream = pictureStorageService.find(picture.getFileName());

            MediaType mediaTypePicture = MediaType.parseMediaType(picture.getContentType());
            List<MediaType> acceptedMediaTypes = MediaType.parseMediaTypes(acceptedMediaType);

            checkIfCompatible(acceptedMediaTypes, mediaTypePicture);

            return ResponseEntity.ok()
                    .contentType(mediaTypePicture)
                    .body(new InputStreamResource(inputStream));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @CheckSecurity.Restaurant.canManageOperation
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductPictureDto updatePicture(@PathVariable("restaurantId") Long restaurantId,
                                           @PathVariable("productId") Long productId, @Valid ProductPictureInputDto inputProductPicture) throws IOException {

        Product product = productRegistrationService.search(productId, restaurantId);
        MultipartFile file = inputProductPicture.getFile();

        ProductPicture picture = new ProductPicture();
        picture.setProduct(product);
        picture.setDescription(inputProductPicture.getDescription());
        picture.setFileName(file.getOriginalFilename());
        picture.setSize(file.getSize());
        picture.setContentType(file.getContentType());

        return productPictureDtoAssembler.toDto(catalogProductPictureService.save(picture, file.getInputStream()));
    }

    @CheckSecurity.Restaurant.canManageOperation
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePicture(@PathVariable("restaurantId") Long restaurantId,
                              @PathVariable("productId") Long productId) {
        catalogProductPictureService.delete(productId, restaurantId);
    }

    private void checkIfCompatible(List<MediaType> acceptedMediaTypes, MediaType mediaTypePicture)
            throws HttpMediaTypeNotAcceptableException {
        boolean compatible = acceptedMediaTypes.stream()
                .anyMatch(obj -> obj.isCompatibleWith(mediaTypePicture));

        if (!compatible) {
            throw new HttpMediaTypeNotAcceptableException(acceptedMediaTypes);
        }
    }
}
