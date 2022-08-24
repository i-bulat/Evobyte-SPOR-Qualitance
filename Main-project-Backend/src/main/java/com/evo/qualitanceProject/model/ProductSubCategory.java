package com.evo.qualitanceProject.model;

import lombok.*;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NamedEntityGraphs({@NamedEntityGraph(name = "subcategoryWithProduct", attributeNodes = @NamedAttributeNode(value = "products"))})

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder

public class ProductSubCategory extends BaseEntity<Long> {

    private String name;

    private String description;


    @ManyToOne(optional = false, fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "productCategoryId")
    private ProductCategory productCategory;


    //    @JsonBackReference
    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();

    public ProductSubCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ProductSubCategory(Long aLong) {
        super(aLong);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductSubCategory that = (ProductSubCategory) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description);
    }


    @Override
    public String toString() {
        return "ProductSubCategory{" + "name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }
}
