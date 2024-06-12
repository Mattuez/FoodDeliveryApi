package com.matheus.fooddeliveryapi.api.model.products.picture;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class ProductPictureDto {

    private String fileName;
    private String description;
    private String contentType;
    private Long size;
}
