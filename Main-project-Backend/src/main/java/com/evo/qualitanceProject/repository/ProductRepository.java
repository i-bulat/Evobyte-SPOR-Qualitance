package com.evo.qualitanceProject.repository;

import com.evo.qualitanceProject.model.Product;
import com.evo.qualitanceProject.model.ProductSubCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends DefaultRepository<Product, Long> {

    @Query("select distinct p from Product p where p.id= :productId")
    @EntityGraph(value = "productWithReviews", type =
            EntityGraph.EntityGraphType.LOAD)
    Product getByIdWithReviews(@Param("productId") Long ProductId);


    @Query("select p, SUM(li.quantity) as quantity from LineItem li" +
            " left join Product p on li.product = p" +
            " left join Order o on li.order = o " +
            " left join ProductSubCategory psc on p.subCategory = psc" +
            " left join ProductCategory pc on psc.productCategory = pc" +
            " where o.updateDate >= :date " +
            " and pc.id = :id" +
            " AND (o.status = 'COMPLETED' OR o.status = 'PLACED') " +
            " GROUP BY p.id " +
            " ORDER BY quantity desc")
    List<Product> getTopProducts(@Param("date") LocalDate date,
                                 @Param("id") Long categoryId,
                                 Pageable pageable);

    @Query("select sum(li.quantity) as quantity from LineItem li" +
            " left join Product p on li.product = p" +
            " left join Order o on li.order = o" +
            " where p.id= :id" +
            " and (o.status = 'COMPLETED' OR o.status = 'PLACED')")
    Long getTotalSales(@Param("id") Long id);

    @Query("select distinct p from Product p where lower(p.name) like %:#{#name.toLowerCase()}%")
    List<Product> findAllByName(@Param("name") String name);

    @Query("select distinct p from Product p where lower(p.name) like %:#{#name.toLowerCase()}% " +
            "or lower(p.description) like %:#{#description.toLowerCase()}%")
    List<Product> findProductsAccordingToSearchBar(@Param("name") String name,
                                                   @Param("description") String description);

    @Query("select p from Product  p where p.subCategory = :subcategory")
    List<Product> getProductsBySubcategory(@Param("subcategory") ProductSubCategory subCategory);
}
