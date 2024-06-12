package com.matheus.fooddeliveryapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "city")
public class City {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            name = "name",
            length = 60,
            nullable = false
    )
    private String name;

    @ManyToOne
    @JoinColumn(
            name = "state_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_city_state_state_id"
            )
    )
    private State state;
}
