package com.evo.qualitanceProject.service;

import com.evo.qualitanceProject.model.LineItem;

import java.util.List;
import java.util.Optional;

public interface LineItemService {

    Optional<LineItem> findLineItemWithProduct(Long id);

    List<LineItem> findAllLineItems();

    LineItem addLineItem(Long product_id, int quantity);

    LineItem updateLineItem(Long id, int quantity);

    void deleteLineItem(Long id);
}
