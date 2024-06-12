package com.matheus.fooddeliveryapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "access_level"
)
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccessLevel {

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "access_level_permissions",
            joinColumns = @JoinColumn(
                    name = "access_level_id",
                    foreignKey = @ForeignKey(
                            name = "fk_access_level_permissions_access_level_access_level_id"
                    )
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "permission_id",
                    foreignKey = @ForeignKey(
                            name = "fk_access_level_permissions_permission_permission_id"
                    )
            )
    )
    private Set<Permission> permissions = new HashSet<>();

    public void addPermission(Permission permission) {
        this.getPermissions().add(permission);
    }

    public void removePermission(Permission permission) {
        this.getPermissions().remove(permission);
    }
}