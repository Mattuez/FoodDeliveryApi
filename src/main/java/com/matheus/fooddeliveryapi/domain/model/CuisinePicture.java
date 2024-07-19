package com.matheus.fooddeliveryapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(
        name = "cuisine_picture"
)
public class CuisinePicture extends Picture {

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Cuisine cuisine;
}
