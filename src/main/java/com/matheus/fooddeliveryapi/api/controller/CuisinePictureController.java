package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.picture.PictureDtoAssembler;
import com.matheus.fooddeliveryapi.api.model.picture.PictureDto;
import com.matheus.fooddeliveryapi.api.model.picture.PictureInputDto;
import com.matheus.fooddeliveryapi.core.security.CheckSecurity;
import com.matheus.fooddeliveryapi.domain.exception.EntityNotFoundException;
import com.matheus.fooddeliveryapi.domain.model.Cuisine;
import com.matheus.fooddeliveryapi.domain.model.CuisinePicture;
import com.matheus.fooddeliveryapi.domain.model.Product;
import com.matheus.fooddeliveryapi.domain.model.ProductPicture;
import com.matheus.fooddeliveryapi.domain.service.CuisinePictureService;
import com.matheus.fooddeliveryapi.domain.service.CuisineRegistrationService;
import com.matheus.fooddeliveryapi.domain.service.PictureStorageService;
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
@RequestMapping("/cuisines/{cuisineId}/picture")
public class CuisinePictureController {

    private CuisineRegistrationService cuisineRegistrationService;
    private PictureDtoAssembler<CuisinePicture> picturePictureDtoAssembler;
    private CuisinePictureService cuisinePictureService;
    private PictureStorageService pictureStorageService;

    @CheckSecurity.Restaurant.canView
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PictureDto getPictureData(@PathVariable("cuisineId") Long cuisineId) {
        return picturePictureDtoAssembler.toDto(cuisinePictureService.search(cuisineId));
    }

    @GetMapping
    public ResponseEntity<?> getPicture(@PathVariable("cuisineId") Long cuisineId,
                                        @RequestHeader(name = "accept") String acceptedMediaType)
            throws HttpMediaTypeNotAcceptableException {

        try {
            CuisinePicture picture = cuisinePictureService.search(cuisineId);
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
    public PictureDto updatePicture(@PathVariable("cuisineId") Long cuisineId,
                                    @Valid PictureInputDto inputProductPicture) throws IOException {

        Cuisine cuisine = cuisineRegistrationService.search(cuisineId);
        MultipartFile file = inputProductPicture.getFile();

        CuisinePicture picture = new CuisinePicture();
        picture.setCuisine(cuisine);
        picture.setDescription(inputProductPicture.getDescription());
        picture.setFileName(file.getOriginalFilename());
        picture.setSize(file.getSize());
        picture.setContentType(file.getContentType());

        return picturePictureDtoAssembler.toDto(cuisinePictureService.save(picture, file.getInputStream()));
    }

    @CheckSecurity.Restaurant.canManageOperation
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePicture(@PathVariable("cuisineId") Long cuisineId) {
        cuisinePictureService.delete(cuisineId);
    }
}
