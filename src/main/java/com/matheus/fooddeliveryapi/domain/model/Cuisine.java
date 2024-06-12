package com.matheus.fooddeliveryapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "cuisine")
public class Cuisine {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @EqualsAndHashCode.Include
    private Long id;

    @Column(
            name = "name",
            length = 30,
            nullable = false
    )
    private String name;

    @OneToMany(
            mappedBy = "cuisine"
    )
    private List<Restaurant> restaurants = new ArrayList<>();
}
