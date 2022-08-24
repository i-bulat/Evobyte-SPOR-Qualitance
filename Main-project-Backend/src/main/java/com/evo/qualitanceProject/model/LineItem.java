package com.evo.qualitanceProject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "lineItems")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class LineItem extends BaseEntity<Long> {

    @Column(name = "quantity", nullable = false)
    int quantity;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonBackReference(value = "date")
    @UpdateTimestamp
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    public LineItem(Order order, Product product, int quantity) {
        this.quantity = quantity;
        this.order = order;
        this.product = product;
    }

    public BigDecimal getTotalPrice() {
        return BigDecimal.valueOf(product.getPrice() * quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LineItem lineItem = (LineItem) o;
        return quantity == lineItem.quantity && Objects.equals(order, lineItem.order) && Objects.equals(product, lineItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), quantity, order, product);
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "quantity=" + quantity +
                ", order=" + order +
                ", product=" + product +
                '}' + super.toString();
    }
}
