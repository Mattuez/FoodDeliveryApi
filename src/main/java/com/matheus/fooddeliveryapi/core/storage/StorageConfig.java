package com.matheus.fooddeliveryapi.core.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.matheus.fooddeliveryapi.domain.service.PictureStorageService;
import com.matheus.fooddeliveryapi.infraestructure.service.storage.LocalPictureStorageService;
import com.matheus.fooddeliveryapi.infraestructure.service.storage.S3PictureStorageService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class StorageConfig {

    private StorageProperties storageProperties;
    private AmazonS3 amazonS3;

    @Bean
    public PictureStorageService pictureStorageService() {
        switch (storageProperties.getImpl()) {
            case LOCAL -> {
                return new LocalPictureStorageService(storageProperties);
            } case S3 -> {
                return new S3PictureStorageService(amazonS3, storageProperties);
            } default -> {
                return null;
            }
        }
    }
}
