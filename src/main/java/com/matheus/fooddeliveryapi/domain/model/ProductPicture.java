package com.matheus.fooddeliveryapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(
        name = "product_picture"
)
public class ProductPicture extends Picture {

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Product product;
}