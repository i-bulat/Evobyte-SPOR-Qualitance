package com.evo.qualitanceProject.service;

import com.evo.qualitanceProject.model.*;
import com.evo.qualitanceProject.repository.LineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LineItemServiceImpl implements LineItemService {

    @Autowired
    private LineItemRepository repository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


    @Override
    public Optional<LineItem> findLineItemWithProduct(Long id) {
        return Optional.of(repository.findById(id).orElseThrow());
    }

    @Override
    public List<LineItem> findAllLineItems() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public LineItem addLineItem(Long product_id, int quantity) {
        AppUser user = userService.getAuthenticatedUser();

        //Check if there is an order with "IN_BASKET" status. Create one if not
        Order order = new Order();
        Set<Order> orders = user.getOrders();
        //todo: use streams
        for (Order ord : orders) {
            if (ord.getStatus().equals(OrderStatusEnum.IN_BASKET)) {
                order = ord;
                break;
            }
        }
        if (order.getStatus() == null) {
            order.setStatus(OrderStatusEnum.IN_BASKET);
            order.setUser(user);
            orderService.saveOrder(order);
        }

        Product prod = productService.findProduct(product_id).orElseThrow();

        //Check stock, save lineItem and replace quantity
        int quantityLeft = (int) prod.getQuantity();
        if (quantityLeft >= quantity) {
            LineItem lineItem = new LineItem(order, prod, quantity);
            prod.setQuantity(quantityLeft - quantity);
            return repository.save(lineItem);
        }
        throw new RuntimeException("only " + prod.getQuantity() + " items left");
    }

    @Override
    @Transactional
    public LineItem updateLineItem(Long id, int quantity) {
        LineItem updatedValue = repository.findById(id).orElseThrow();
        updatedValue.setQuantity(quantity);

        return updatedValue;
    }

    @Override
    @Transactional
    public void deleteLineItem(Long id) {
        LineItem lineItem=repository.findById(id).orElseThrow();

        //restoring product stock
        Product prod=lineItem.getProduct();
        int quantity=lineItem.getQuantity();
        prod.setQuantity(prod.getQuantity()+quantity);

        repository.deleteById(id);
    }

}
