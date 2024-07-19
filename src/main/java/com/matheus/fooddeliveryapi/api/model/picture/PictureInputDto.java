package com.matheus.fooddeliveryapi.api.model.picture;

import com.matheus.fooddeliveryapi.core.validation.annotation.FileContentType;
import com.matheus.fooddeliveryapi.core.validation.annotation.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PictureInputDto {

    @NotNull
    @FileSize(max = "500KB")
    @FileContentType(allowedMediaType = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    private MultipartFile file;

    @NotBlank
    private String description;
}
