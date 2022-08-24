package com.evo.qualitanceProject.repository;

import com.evo.qualitanceProject.model.ProductCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductCategoryRepository extends DefaultRepository<ProductCategory, Long> {

    @Query("select pc, SUM(li.quantity) as quantity from LineItem li" +
            " left join Product p on li.product = p" +
            " left join Order o on li.order = o " +
            " left join ProductSubCategory psc on p.subCategory = psc" +
            " left join ProductCategory pc on psc.productCategory = pc" +
            " where o.updateDate >= :date " +
            " AND (o.status = 'COMPLETED' OR o.status = 'PLACED') " +
            " GROUP BY pc.id" +
            " ORDER BY quantity desc")
    List<ProductCategory> getTopCategories(@Param("date") LocalDate date,
                                           Pageable pageable);

}
