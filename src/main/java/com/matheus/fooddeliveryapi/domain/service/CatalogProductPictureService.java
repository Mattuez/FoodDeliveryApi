package com.matheus.fooddeliveryapi.domain.service;

import com.matheus.fooddeliveryapi.domain.exception.PictureNotFound;
import com.matheus.fooddeliveryapi.domain.model.Product;
import com.matheus.fooddeliveryapi.domain.model.ProductPicture;
import com.matheus.fooddeliveryapi.domain.model.Restaurant;
import com.matheus.fooddeliveryapi.domain.repository.ProductPictureRepository;
import com.matheus.fooddeliveryapi.infraestructure.service.storage.LocalPictureStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogProductPictureService {

    private ProductPictureRepository pictureRepository;
    private PictureStorageService pictureStorageService;

    public CatalogProductPictureService(ProductPictureRepository pictureRepository,
                                        PictureStorageService localPictureStorageService) {
        this.pictureRepository = pictureRepository;
        this.pictureStorageService = localPictureStorageService;
    }

    public ProductPicture search(Long productId, Long restaurantId) {
        return pictureRepository.findByProductIdAndRestaurantId(productId, restaurantId)
                .orElseThrow(() -> new PictureNotFound(productId, restaurantId));
    }

    @Transactional
    public ProductPicture save(ProductPicture picture, InputStream inputStream) {
        Long productId = picture.getProduct().getId();
        Long restaurantId = picture.getProduct().getRestaurant().getId();
        String newFileName = pictureStorageService.generateFileName(picture.getFileName());
        String existingFileName = null;

        Optional<ProductPicture> existingPicture = pictureRepository
                .findByProductIdAndRestaurantId(productId, restaurantId);

        if (existingPicture.isPresent()) {
            existingFileName = existingPicture.get().getFileName();
            pictureRepository.delete(existingPicture.get());
        }

        picture.setFileName(newFileName);
        ProductPicture savedPicture = pictureRepository.save(picture);
        pictureRepository.flush();

        PictureStorageService.NewPicture newPicture = PictureStorageService.NewPicture.builder()
                .name(picture.getFileName())
                .inputStream(inputStream)
                .contentType(picture.getContentType())
                .build();

        pictureStorageService.replace(newPicture, existingFileName);

        return savedPicture;
    }

    @Transactional
    public void delete(Long productId, Long restaurantId) {
        ProductPicture productPicture = search(productId, restaurantId);

        pictureRepository.delete(productPicture);
        pictureStorageService.delete(productPicture.getFileName());
    }
}
