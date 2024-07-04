package com.matheus.fooddeliveryapi.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface PictureStorageService {

    RetrievedPicture find(String fileName);
    void store(NewPicture newPicture);
    void delete(String pictureName);

    default void replace(NewPicture newPicture, String oldPictureName) {
        this.store(newPicture);

        if (oldPictureName != null) {
            delete(oldPictureName);
        }
    }

    default String generateFileName(String originalName) {
        return "%s_%s".formatted(UUID.randomUUID(), originalName);
    }

    @Getter
    @Builder
    class NewPicture {
        private String name;
        private String contentType;
        private InputStream inputStream;
    }

    @Builder
    @Getter
    class RetrievedPicture {
        private InputStream inputStream;
        private String url;

        public boolean hasUrl() {
            return url != null;
        }
    }
}
