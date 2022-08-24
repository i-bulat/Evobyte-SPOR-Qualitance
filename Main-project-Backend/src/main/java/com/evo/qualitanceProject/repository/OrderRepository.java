package com.evo.qualitanceProject.repository;

import com.evo.qualitanceProject.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CustomOrderRepository {

    @Query("select distinct o from Order o")
    @EntityGraph(value = "orderWithLineItem", type =
            EntityGraph.EntityGraphType.LOAD)
    List<Order> findAllWithLineItem();

    @Query("select distinct o from Order o where o.id=?1")
    @EntityGraph(value = "orderWithLineItem", type =
            EntityGraph.EntityGraphType.LOAD)
    Optional<Order> findByIdWithLineItem(Long id);
}
