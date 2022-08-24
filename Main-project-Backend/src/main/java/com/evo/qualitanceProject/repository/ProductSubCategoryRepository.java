package com.evo.qualitanceProject.repository;

import com.evo.qualitanceProject.model.ProductSubCategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSubCategoryRepository extends DefaultRepository<ProductSubCategory, Long> {
    @Query("select distinct c from ProductSubCategory c")
    @EntityGraph(value = "subcategoryWithProduct", type =
            EntityGraph.EntityGraphType.LOAD)
    List<ProductSubCategory> findAllWithProduct();

    @Query("select distinct c from ProductSubCategory c where c.id=?1")
    @EntityGraph(value = "subcategoryWithProduct", type =
            EntityGraph.EntityGraphType.LOAD)
    Optional<ProductSubCategory> findByIdWithProduct(Long id);

    @Query("delete from ProductSubCategory c where c.id=?1")
    @Modifying
    @EntityGraph(value = "subcategoryWithProduct", type =
            EntityGraph.EntityGraphType.LOAD)
    void deleteByIdWithProduct(Long id);


}