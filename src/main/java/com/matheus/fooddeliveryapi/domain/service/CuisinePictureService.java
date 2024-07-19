package com.matheus.fooddeliveryapi.domain.service;

import com.matheus.fooddeliveryapi.domain.exception.CuisinePictureNotFoundException;
import com.matheus.fooddeliveryapi.domain.model.CuisinePicture;
import com.matheus.fooddeliveryapi.domain.model.ProductPicture;
import com.matheus.fooddeliveryapi.domain.repository.CuisinePictureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CuisinePictureService {

    private CuisinePictureRepository pictureRepository;
    private PictureStorageService pictureStorageService;

    public CuisinePicture search(Long cuisineId) {
        return pictureRepository.findById(cuisineId)
                .orElseThrow(() -> new CuisinePictureNotFoundException(cuisineId));
    }

    @Transactional
    public CuisinePicture save(CuisinePicture picture, InputStream inputStream) {
        Long cuisineId = picture.getCuisine().getId();
        String newFileName = pictureStorageService.generateFileName(picture.getFileName());
        String existingFileName = null;

        Optional<CuisinePicture> existingPicture = pictureRepository.findById(cuisineId);

        if (existingPicture.isPresent()) {
            existingFileName = existingPicture.get().getFileName();
            pictureRepository.delete(existingPicture.get());
        }

        picture.setFileName(newFileName);
        CuisinePicture savedPicture = pictureRepository.save(picture);
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
    public void delete(Long cuisineId) {
        CuisinePicture cuisinePicture = search(cuisineId);

        pictureRepository.delete(cuisinePicture);
        pictureStorageService.delete(cuisinePicture.getFileName());
    }
}
