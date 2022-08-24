package com.evo.qualitanceProject.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NamedEntityGraphs({@NamedEntityGraph(name = "productWithReviews",
        attributeNodes = @NamedAttributeNode(value = "reviews"))})

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Product extends BaseEntity<Long> {
    private String name;
    private String imageURL;
    private String description;
    private long price;
    private long quantity;

    @JsonBackReference
    @ManyToOne(optional = false)
    private ProductSubCategory subCategory;

    @JsonBackReference(value = "show_reviews")
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Product product = (Product) o;
        return price == product.price && quantity == product.quantity && Objects.equals(name, product.name) && Objects.equals(imageURL, product.imageURL) && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, imageURL, description, price, quantity);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                "} " + super.toString();
    }
}
