package com.matheus.fooddeliveryapi.infraestructure.service.storage;

import com.matheus.fooddeliveryapi.core.storage.StorageProperties;
import com.matheus.fooddeliveryapi.domain.exception.StorageException;
import com.matheus.fooddeliveryapi.domain.service.PictureStorageService;
import lombok.AllArgsConstructor;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@AllArgsConstructor
public class LocalPictureStorageService implements PictureStorageService {

    private StorageProperties storageProperties;

    @Override
    public RetrievedPicture find(String fileName) {
        try {
            Path filePath = getFilePath(fileName);

            return RetrievedPicture.builder()
                    .inputStream(Files.newInputStream(filePath))
                    .build();
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
        return storageProperties.getLocal()
                .getPicturesDirectory().resolve(fileName);
    }
}
