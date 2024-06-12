package com.matheus.fooddeliveryapi.domain.model;

import com.matheus.fooddeliveryapi.domain.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @EqualsAndHashCode.Include
    private Long id;

    @Column(
            name = "code",
            nullable = false
    )
    private String code;

    @Column(
            name = "subtotal",
            nullable = false
    )
    private BigDecimal subtotal;

    @Column(
            name = "delivery_fee",
            nullable = false
    )
    private BigDecimal deliveryFee;

    @Column(
            name = "total_amount",
            nullable = false
    )
    private BigDecimal totalAmount;

    @Embedded
    private Address address;

    @Column(
            name = "order_status"
    )
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.CREATED;

    @CreationTimestamp
    @Column(
            name = "creation_date",
            nullable = false
    )
    private OffsetDateTime creationDate;

    @Column(
            name = "confirmation_date",
            nullable = true
    )
    private OffsetDateTime confirmationDate;

    @Column(
            name = "delivery_date",
            nullable = true
    )
    private OffsetDateTime deliveryDate;

    @Column(
            name = "cancellation_date",
            nullable = true
    )
    private OffsetDateTime cancellationDate;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "payment_method_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_order_payment_method_payment_method_id"
            )
    )
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(
            name = "restaurant_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_order_restaurant_restaurant_id"
            )
    )
    private Restaurant restaurant;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL
    )
    private List<OrderedProduct> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_order_user_user_id"
            )
    )
    private User client;

    public void calculateTotalAmount() {
        getItems().forEach(OrderedProduct::calculateTotalPrice);

        this.subtotal = getItems().stream()
                .map(OrderedProduct::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalAmount = this.subtotal.add(this.deliveryFee);
    }

    public void confirm() {
        setOrderStatus(OrderStatus.CONFIRMED);
        setConfirmationDate(OffsetDateTime.now());
    }

    public void deliver() {
        setOrderStatus(OrderStatus.DELIVERED);
        setDeliveryDate(OffsetDateTime.now());
    }

    public void cancel() {
        setOrderStatus(OrderStatus.CANCELED);
        setCancellationDate(OffsetDateTime.now());
    }

    private void setOrderStatus(OrderStatus orderStatus) {
        if (!this.getOrderStatus().canChangeTo(orderStatus)) {
            throw new BusinessException("Status from order of code %s can't be changed from %s to %s"
                    .formatted(getCode(), getOrderStatus(), orderStatus));
        }

        this.orderStatus = orderStatus;
    }

    @PrePersist
    private void generateCode() {
        setCode(UUID.randomUUID().toString());
    }
}
