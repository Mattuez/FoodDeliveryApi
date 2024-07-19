package com.matheus.fooddeliveryapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(
        name = "restaurant"
)
public class Restaurant {

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
            name = "delivery_fee",
            nullable = false
    )
    private BigDecimal deliveryFee;

    @Column(
            name = "active",
            nullable = false
    )
    private Boolean active = Boolean.TRUE;

    @Column(
            name = "opened",
            nullable = false
    )
    private Boolean opened = Boolean.TRUE;

    @Column(
            name = "verified",
            nullable = false
    )
    private Boolean verified = Boolean.FALSE;

    @Embedded
    private Address address;

    @CreationTimestamp
    @Column(
            name = "register_date",
            nullable = false,
            columnDefinition = "datetime"
    )
    private OffsetDateTime registerDate;

    @UpdateTimestamp
    @Column(
            name = "update_date",
            nullable = false,
            columnDefinition = "datetime"
    )
    private OffsetDateTime updateDate;


    @ManyToOne
    @JoinColumn(
            name = "cuisine_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_restaurant_cuisine_cuisine_id"
            )
    )
    private Cuisine cuisine;

    @ManyToMany
    @JoinTable(
            name = "restaurant_payment_methods",
            joinColumns = @JoinColumn(
                    name =  "restaurant_id",
                    foreignKey = @ForeignKey(
                        name = "fk_restaurant_payment_methods_restaurant_restaurant_id"
                    )
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "payment_method_id",
                    foreignKey = @ForeignKey(
                            name = "fk_restaurant_payment_methods_payment_method_payment_method_id"
                    )
            )
    )
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "restaurant_user_admin",
            joinColumns = @JoinColumn(
                    name = "restaurant_id",
                    foreignKey = @ForeignKey(
                            name = "fk_restaurant_user_admin_restaurant_restaurant_id"
                    )
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id",
                    foreignKey = @ForeignKey(
                            name = "fk_restaurant_user_admin_user_user_id"
                    )
            )
    )
    private Set<User> admins = new HashSet<>();

    @OneToMany(
            mappedBy = "restaurant"
    )
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    private List<Order> orders = new ArrayList<>();

    public void activate() {
        this.setActive(true);
    }

    public void deactivate() {
        this.setActive(false);
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) {
        this.getPaymentMethods().add(paymentMethod);
    }

    public void removePaymentMethod(PaymentMethod paymentMethod) {
        this.getPaymentMethods().remove(paymentMethod);
    }

    public boolean isPaymentMethodAccepted(PaymentMethod paymentMethod) {
        return this.getPaymentMethods().contains(paymentMethod);
    }

    public void open() {
        this.setOpened(true);
    }

    public void close() {
        this.setOpened(false);
    }

    public void addAdmin(User admin) {
        this.getAdmins().add(admin);
    }

    public void removeAdmin(User admin) {
        this.getAdmins().remove(admin);
    }

    public void verify() {
        this.setVerified(true);
    }

    public void unverify() {
        this.setVerified(false);
    }
}
