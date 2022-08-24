package com.evo.qualitanceProject.controller;

import com.evo.qualitanceProject.model.Order;
import com.evo.qualitanceProject.model.OrderStatusEnum;
import com.evo.qualitanceProject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping
    List<Order> findAllOrders() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    Order findOrderById(@PathVariable Long id) {
        return service.findOrder(id).get();
    }

    @PostMapping
    Order saveOrder(@RequestBody Order order) {
        return service.saveOrder(order);
    }


    @PutMapping("/{id}")
    Order updateOrderStatus(@PathVariable Long id,
                            @RequestParam OrderStatusEnum status) {
        return service.updateOrderStatus(id, status);
    }


    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        service.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/placeOrder")
    Order placeOrder(@RequestParam Long orderId,
                     @RequestParam String address,
                     @RequestParam boolean saveAddressForLater) {
        return service.placeOrder(orderId, address, saveAddressForLater);
    }

    @GetMapping("/validateCreditCard")
    ResponseEntity<?> validateCreditCard(@RequestParam String cardNo,
                                         @RequestParam String expirationDate,
                                         @RequestParam String CVV) {
        boolean valid = service.validateCreditCard(cardNo, expirationDate, CVV);
        if (!valid)
            return ResponseEntity.badRequest().body("invalid input");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
