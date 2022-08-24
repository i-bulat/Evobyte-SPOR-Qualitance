package com.evo.qualitanceProject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NamedEntityGraphs({@NamedEntityGraph(name = "orderWithLineItem",
        attributeNodes = @NamedAttributeNode(value = "lineItems"))})

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order extends BaseEntity<Long> {

    @CreationTimestamp
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate creationDate;

    @UpdateTimestamp
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate updateDate;

    @Column(nullable = false)
    private OrderStatusEnum status;

    @Column(nullable=true)
    private String address;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private AppUser user;


    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<LineItem> lineItems = new HashSet<>();

    public Order(OrderStatusEnum status, AppUser user) {
        this.status = status;
        this.user = user;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (LineItem item : lineItems) {
            total = total.add(item.getTotalPrice());
        }
        return total;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(creationDate, order.creationDate) && Objects.equals(updateDate, order.updateDate) && status == order.status && Objects.equals(address, order.address) && Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), creationDate, updateDate, status, address, user);
    }

    @Override
    public String toString() {
        return "Order{" +
                "creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", status=" + status +
                ", address='" + address + '\'' +
                ", user=" + user +
                '}';
    }
}
