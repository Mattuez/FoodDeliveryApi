package com.matheus.fooddeliveryapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(
        name = "product_picture"
)
public class ProductPicture {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "product_id")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "description")
    private String description;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "size")
    private Long size;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Product product;
}