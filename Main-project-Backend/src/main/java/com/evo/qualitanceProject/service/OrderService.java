package com.evo.qualitanceProject.service;

import com.evo.qualitanceProject.model.Order;
import com.evo.qualitanceProject.model.OrderStatusEnum;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findAll();

    Optional<Order> findOrder(Long id);

    Order saveOrder(Order order);

    Order updateOrderStatus(Long id, OrderStatusEnum status);

    void deleteOrder(Long id);

    Order placeOrder(Long id, String address, boolean saveAddressForLater);

    boolean validateCreditCard(String cardNo, String expirationDate, String CVV);
}
