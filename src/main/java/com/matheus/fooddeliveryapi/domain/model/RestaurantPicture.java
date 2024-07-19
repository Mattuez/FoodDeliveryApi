package com.matheus.fooddeliveryapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(
        name = "restaurant_picture"
)
public class RestaurantPicture extends Picture {

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;
}
