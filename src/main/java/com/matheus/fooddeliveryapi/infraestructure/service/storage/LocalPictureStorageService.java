package com.matheus.fooddeliveryapi.infraestructure.service.storage;

import com.matheus.fooddeliveryapi.domain.exception.StorageException;
import com.matheus.fooddeliveryapi.domain.service.PictureStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalPictureStorageService implements PictureStorageService {

    @Value("${foodDeliveryApi.storage.local.pictures-directory}")
    private Path pictureDirectory;

    @Override
    public InputStream find(String fileName) {
        try {
            Path filePath = getFilePath(fileName);

            return Files.newInputStream(filePath);
        } catch (Exception e) {
            throw new StorageException("It was not possible to find file", e);
        }
    }

    @Override
    public void store(NewPicture newPicture) {
        try {
            Path filePath = getFilePath(newPicture.getName());

            FileCopyUtils.copy(newPicture.getInputStream(), Files.newOutputStream(filePath));
        } catch (Exception e) {
            throw new StorageException("It was not possible to store file", e);
        }
    }

    @Override
    public void delete(String pictureName) {
        try {
            Path filePath = getFilePath(pictureName);
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new StorageException("It was not possible to delete file", e);
        }
    }

    private Path getFilePath(String fileName) {
        return pictureDirectory.resolve(fileName);
    }
}
