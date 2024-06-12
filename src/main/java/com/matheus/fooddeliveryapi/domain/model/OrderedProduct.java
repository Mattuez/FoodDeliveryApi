package com.matheus.fooddeliveryapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "ordered_product")
public class OrderedProduct {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @EqualsAndHashCode.Include
    private Long id;

    @Column(
            name = "quantity",
            nullable = false
    )
    private Integer quantity;

    @Column(
            name = "unit_price",
            nullable = false
    )
    private BigDecimal unitPrice;

    @Column(
            name = "total_price",
            nullable = false
    )
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_ordered_product_product_product_id"
            )
    )
    private Product product;

    @ManyToOne
    @JoinColumn(
            name = "order_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_ordered_product_order_order_id"
            )
    )
    private Order order;

    @Column(
            name = "note"
    )
    private String note;

    public void calculateTotalPrice() {
        BigDecimal unitPrice = this.getUnitPrice();
        Integer quantity = this.getQuantity();

        if (quantity == null) {
            quantity = 0;
        }

        if (unitPrice == null) {
            unitPrice = BigDecimal.ZERO;
        }

        this.setTotalPrice(unitPrice.multiply(new BigDecimal(quantity)));
    }
}
