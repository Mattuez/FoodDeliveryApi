package com.matheus.fooddeliveryapi.core.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.nio.file.Path;

@Getter
@Setter
@Component
@ConfigurationProperties("food-delivery-api.storage")
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();

    private StorageImplementation impl = StorageImplementation.LOCAL;

    public enum StorageImplementation {
        S3, LOCAL
    }

    @Getter
    @Setter
    public class Local {
        @NotNull
        private Path picturesDirectory;
    }

    @Getter
    @Setter
    public class S3 {
        @NotNull
        private String idAccessKey;

        @NotNull
        private String secretAccessKey;

        @NotNull
        private String bucket;

        @NotNull
        private String region;

        @NotNull
        private String directoryPhotos;
    }

}
