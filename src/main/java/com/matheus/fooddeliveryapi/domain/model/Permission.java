package com.matheus.fooddeliveryapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "permission")
public class Permission {

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

    @Column(
            name = "description",
            nullable = false
    )
    private String description;
}
