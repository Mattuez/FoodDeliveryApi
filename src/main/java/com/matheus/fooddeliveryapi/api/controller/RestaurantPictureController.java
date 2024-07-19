package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.picture.PictureDtoAssembler;
import com.matheus.fooddeliveryapi.api.model.picture.PictureDto;
import com.matheus.fooddeliveryapi.api.model.picture.PictureInputDto;
import com.matheus.fooddeliveryapi.core.security.CheckSecurity;
import com.matheus.fooddeliveryapi.domain.exception.EntityNotFoundException;
import com.matheus.fooddeliveryapi.domain.model.Restaurant;
import com.matheus.fooddeliveryapi.domain.model.RestaurantPicture;
import com.matheus.fooddeliveryapi.domain.service.CatalogRestaurantPictureService;
import com.matheus.fooddeliveryapi.domain.service.PictureStorageService;
import com.matheus.fooddeliveryapi.domain.service.RestaurantRegistrationService;
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
@RequestMapping("/restaurants/{restaurantId}/picture")
public class RestaurantPictureController {

    private RestaurantRegistrationService restaurantRegistrationService;
    private PictureDtoAssembler<RestaurantPicture> picturePictureDtoAssembler;
    private CatalogRestaurantPictureService catalogRestaurantPictureService;
    private PictureStorageService pictureStorageService;

    @CheckSecurity.Restaurant.canView
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PictureDto getPictureData(@PathVariable("restaurantId") Long restaurantId) {
        return picturePictureDtoAssembler.toDto(catalogRestaurantPictureService.search(restaurantId));
    }

    @GetMapping
    public ResponseEntity<?> getPicture(@PathVariable("restaurantId") Long restaurantId,
                                        @RequestHeader(name = "accept") String acceptedMediaType)
            throws HttpMediaTypeNotAcceptableException {

        try {
            RestaurantPicture picture = catalogRestaurantPictureService.search(restaurantId);
            PictureStorageService.RetrievedPicture retrievedPicture = pictureStorageService.find(picture.getFileName());

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

    @CheckSecurity.Restaurant.canManageOperation
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PictureDto updatePicture(@PathVariable("restaurantId") Long restaurantId,
                                    @Valid PictureInputDto inputProductPicture) throws IOException {

        Restaurant restaurant = restaurantRegistrationService.search(restaurantId);
        MultipartFile file = inputProductPicture.getFile();

        RestaurantPicture picture = new RestaurantPicture();
        picture.setRestaurant(restaurant);
        picture.setDescription(inputProductPicture.getDescription());
        picture.setFileName(file.getOriginalFilename());
        picture.setSize(file.getSize());
        picture.setContentType(file.getContentType());

        return picturePictureDtoAssembler.toDto(catalogRestaurantPictureService.save(picture, file.getInputStream()));
    }

    @CheckSecurity.Restaurant.canManageOperation
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePicture(@PathVariable("restaurantId") Long restaurantId) {
        catalogRestaurantPictureService.delete(restaurantId);
    }
}
