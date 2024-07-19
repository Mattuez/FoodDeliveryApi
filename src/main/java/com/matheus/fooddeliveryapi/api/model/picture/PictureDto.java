package com.matheus.fooddeliveryapi.api.model.picture;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PictureDto {

    private String fileName;
    private String description;
    private String contentType;
    private Long size;
}
