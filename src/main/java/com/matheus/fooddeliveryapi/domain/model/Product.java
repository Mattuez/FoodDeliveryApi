package com.matheus.fooddeliveryapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @EqualsAndHashCode.Include
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            length = 30
    )
    private String name;

    @Column(
            name = "description",
            nullable = false
    )
    private String description;

    @Column(
            name = "price",
            nullable = false
    )
    private BigDecimal price;

    @Column(
            name = "active",
            nullable = false
    )
    private Boolean active;

    @ManyToOne
    @JoinColumn(
            name = "restaurant_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_product_restaurant_restaurant_id"
            )
    )
    private Restaurant restaurant;
}
