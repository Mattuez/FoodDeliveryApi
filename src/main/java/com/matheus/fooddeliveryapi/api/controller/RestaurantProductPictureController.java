package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.picture.PictureDtoAssembler;
import com.matheus.fooddeliveryapi.api.model.picture.PictureDto;
import com.matheus.fooddeliveryapi.api.model.picture.PictureInputDto;
import com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation.RestaurantProductPictureOpenApi;
import com.matheus.fooddeliveryapi.core.security.CheckSecurity;
import com.matheus.fooddeliveryapi.domain.exception.EntityNotFoundException;
import com.matheus.fooddeliveryapi.domain.model.Product;
import com.matheus.fooddeliveryapi.domain.model.ProductPicture;
import com.matheus.fooddeliveryapi.domain.service.CatalogProductPictureService;
import com.matheus.fooddeliveryapi.domain.service.PictureStorageService;
import com.matheus.fooddeliveryapi.domain.service.PictureStorageService.RetrievedPicture;
import com.matheus.fooddeliveryapi.domain.service.ProductRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/picture")
public class RestaurantProductPictureController implements RestaurantProductPictureOpenApi {

    private CatalogProductPictureService catalogProductPictureService;
    private ProductRegistrationService productRegistrationService;
    private PictureDtoAssembler<ProductPicture> pictureDtoAssembler;
    private PictureStorageService pictureStorageService;

    @Override
    @CheckSecurity.Restaurant.canView
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PictureDto getPictureData(@PathVariable("restaurantId") Long restaurantId,
                                     @PathVariable("productId") Long productId) {
        return pictureDtoAssembler.toDto(catalogProductPictureService.search(productId, restaurantId));
    }

    @Override
    @GetMapping
    public ResponseEntity<?> getPicture(@PathVariable("restaurantId") Long restaurantId,
                                        @PathVariable("productId") Long productId,
                                        @RequestHeader(name = "accept") String acceptedMediaType)
            throws HttpMediaTypeNotAcceptableException{

        try {
            ProductPicture picture = catalogProductPictureService.search(productId, restaurantId);
            RetrievedPicture retrievedPicture = pictureStorageService.find(picture.getFileName());

            MediaType mediaTypePicture = MediaType.parseMediaType(picture.getContentType());
            List<MediaType> acceptedMediaTypes = MediaType.parseMediaTypes(acceptedMediaType);

            pictureStorageService.checkIfCompatible(acceptedMediaTypes, mediaTypePicture);

            if (retrievedPicture.hasUrl()) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, retrievedPicture.getUrl())
                        .build();
            } else {
                return ResponseEntity.ok()
                        .contentType(mediaTypePicture)
                        .body(new InputStreamResource(retrievedPicture.getInputStream()));
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @CheckSecurity.Restaurant.canManageOperation
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PictureDto updatePicture(@PathVariable("restaurantId") Long restaurantId,
                                    @PathVariable("productId") Long productId,
                                    @Valid PictureInputDto inputProductPicture) throws IOException {

        Product product = productRegistrationService.search(productId, restaurantId);
        MultipartFile file = inputProductPicture.getFile();

        ProductPicture picture = new ProductPicture();
        picture.setProduct(product);
        picture.setDescription(inputProductPicture.getDescription());
        picture.setFileName(file.getOriginalFilename());
        picture.setSize(file.getSize());
        picture.setContentType(file.getContentType());

        return pictureDtoAssembler.toDto(catalogProductPictureService.save(picture, file.getInputStream()));
    }

    @Override
    @CheckSecurity.Restaurant.canManageOperation
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePicture(@PathVariable("restaurantId") Long restaurantId,
                              @PathVariable("productId") Long productId) {
        catalogProductPictureService.delete(productId, restaurantId);
    }
}
