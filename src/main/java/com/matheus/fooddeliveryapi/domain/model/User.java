package com.matheus.fooddeliveryapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @EqualsAndHashCode.Include
    private Long id;

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @Column(
            name = "email",
            nullable = false
    )
    private String email;

    @Column(
            name = "password",
            nullable = false
    )
    private String password;

    @CreationTimestamp
    @Column(
            name = "register_date",
            nullable = false
    )
    private OffsetDateTime registerDate;

    @ManyToMany
    @JoinTable(
            name = "user_access_level",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    foreignKey = @ForeignKey(
                            name = "fk_user_access_level_user_user_id"
                    )
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "access_level_id",
                    foreignKey = @ForeignKey(
                            name = "fk_user_access_level_access_level_access_level_id"
                    )
            )
    )
    private Set<AccessLevel> accessLevel;

    public void addAccessLevel(AccessLevel accessLevel) {
        this.accessLevel.add(accessLevel);
    }

    public void removeAccessLevel(AccessLevel accessLevel) {
        this.accessLevel.remove(accessLevel);
    }
}
