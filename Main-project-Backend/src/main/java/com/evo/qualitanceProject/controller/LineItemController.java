package com.evo.qualitanceProject.controller;

import com.evo.qualitanceProject.converter.LineItemConverter;
import com.evo.qualitanceProject.dto.LineItemDto;
import com.evo.qualitanceProject.model.LineItem;
import com.evo.qualitanceProject.service.LineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/lineItems")
public class LineItemController {

    @Autowired
    private LineItemService service;

    @Autowired
    private LineItemConverter converter;

    @GetMapping
    List<LineItem> getAllLineItemsWithProducts() {
        return service.findAllLineItems();
    }

    @GetMapping("/{id}")
    LineItem getLineItemWithProduct(@PathVariable Long id) {
        return service.findLineItemWithProduct(id).get();
    }

    @GetMapping("/noProduct/{id}")
    LineItemDto getLineItem(@PathVariable Long id) {
        return converter.convertModelToDto(service.findLineItemWithProduct(id).get());
    }

    @PostMapping("/{productId}/{quantity}")
    LineItemDto addLineItem(@PathVariable Long productId,
                         @PathVariable int quantity) {
        return converter.convertModelToDto(service.addLineItem(productId, quantity));
    }

    @PutMapping("/{id}")
    LineItem updateLineItem(@PathVariable Long id,
                            @RequestBody int quantity) {
        return service.updateLineItem(id, quantity);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteLineItem(@PathVariable Long id) {
        service.deleteLineItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
