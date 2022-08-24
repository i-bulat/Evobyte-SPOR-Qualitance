package com.evo.qualitanceProject.service;

import com.evo.qualitanceProject.model.Order;
import com.evo.qualitanceProject.model.OrderStatusEnum;
import com.evo.qualitanceProject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserService userService;

    @Override
    public List<Order> findAll() {
        return repository.findAllWithLineItem();
    }

    @Override
    public Optional<Order> findOrder(Long id) {
        Optional<Order> order = repository.findByIdWithLineItem(id);
        return order;
    }

    @Override
    public Order saveOrder(Order order) {
        return repository.save(order);
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long id, OrderStatusEnum status) {
        Order updatedOrder = repository.findByIdWithLineItem(id).orElseThrow();
        updatedOrder.setStatus(status);
        //updatedOrder.setDateCreated(LocalDate.now());
        return updatedOrder;
    }

    @Override
    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Order placeOrder(Long orderId, String address, boolean saveAddressForLater) {
        Order updatedOrder = repository.findByIdWithLineItem(orderId).orElseThrow();

        updatedOrder.setStatus(OrderStatusEnum.PLACED);
        updatedOrder.setAddress(address);

        if (saveAddressForLater) {
            userService.getAuthenticatedUser().setAddress(address);
        }

        return updatedOrder;
    }

    @Override
    public boolean validateCreditCard(String cardNo, String expirationDate, String CVV) {
        if (cardNo.length() != 16 && cardNo.matches("[0-9]+"))
            return false;

        if (expirationDate.length() != 5)
            return false;

        if (CVV.length() != 3)
            return false;

        return true;
    }
}
