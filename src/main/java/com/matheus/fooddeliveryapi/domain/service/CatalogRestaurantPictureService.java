package com.matheus.fooddeliveryapi.domain.service;

import com.matheus.fooddeliveryapi.domain.exception.RestaurantPictureNotFoundException;
import com.matheus.fooddeliveryapi.domain.model.RestaurantPicture;
import com.matheus.fooddeliveryapi.domain.repository.RestaurantPictureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CatalogRestaurantPictureService {

    private RestaurantPictureRepository pictureRepository;
    private PictureStorageService pictureStorageService;

    public RestaurantPicture search(Long restaurantId) {
        return pictureRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantPictureNotFoundException(restaurantId));
    }

    @Transactional
    public RestaurantPicture save(RestaurantPicture picture, InputStream inputStream) {
        Long restaurantId = picture.getRestaurant().getId();
        String newFileName = pictureStorageService.generateFileName(picture.getFileName());
        String existingFileName = null;

        Optional<RestaurantPicture> existingPicture = pictureRepository.findById(restaurantId);

        if (existingPicture.isPresent()) {
            existingFileName = existingPicture.get().getFileName();
            pictureRepository.delete(existingPicture.get());
        }

        picture.setFileName(newFileName);
        RestaurantPicture savedPicture = pictureRepository.save(picture);
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
    public void delete(Long restaurantId) {
        RestaurantPicture restaurant = search(restaurantId);

        pictureRepository.delete(restaurant);
        pictureStorageService.delete(restaurant.getFileName());
    }
}
