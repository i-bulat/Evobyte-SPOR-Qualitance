package com.evo.qualitanceProject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProductCategory extends BaseEntity<Long> {
    private String name;
    private String description;

    @JsonBackReference
    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<ProductSubCategory> subcategories = new HashSet<>();

    public ProductCategory(Long aLong) {
        super(aLong);
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductCategory that = (ProductCategory) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description);
    }
}
