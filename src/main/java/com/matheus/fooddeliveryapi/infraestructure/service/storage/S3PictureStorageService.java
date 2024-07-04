package com.matheus.fooddeliveryapi.infraestructure.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.matheus.fooddeliveryapi.core.storage.StorageProperties;
import com.matheus.fooddeliveryapi.domain.exception.StorageException;
import com.matheus.fooddeliveryapi.domain.service.PictureStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;

@AllArgsConstructor
public class S3PictureStorageService implements PictureStorageService {

    private AmazonS3 amazonS3;
    private StorageProperties storageProperties;

    @Override
    public RetrievedPicture find(String fileName) {
        String filePath = getFilePath(fileName);

        URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), filePath);

        return RetrievedPicture.builder()
                .url(url.toString())
                .build();
    }

    @Override
    public void store(NewPicture newPicture) {
        try {
            String filePath = getFilePath(newPicture.getName());

            var objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(newPicture.getContentType());

            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    filePath,
                    newPicture.getInputStream(),
                    objectMetaData
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("It was not possible to send file to Amazon S3", e);
        }
    }

    @Override
    public void delete(String pictureName) {
        try {
            String filePath = getFilePath(pictureName);

            var deleteObjectRequest = new DeleteObjectRequest(
                    storageProperties.getS3().getBucket(),
                    filePath
            );

            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("It was not possible to delete file from Amazon S3", e);
        }
    }

    private String getFilePath(String fileName) {
        return String.format("%s/%s", storageProperties.getS3().getDirectoryPhotos(), fileName);
    }
}
