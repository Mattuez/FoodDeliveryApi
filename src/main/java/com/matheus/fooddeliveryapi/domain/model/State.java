package com.matheus.fooddeliveryapi.domain.model;

import com.matheus.fooddeliveryapi.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "state")
public class State {

    @Id
    @NotNull(groups = Groups.StateId.class)
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            name = "name",
            length = 30,
            nullable = false
    )
    private String name;
}
