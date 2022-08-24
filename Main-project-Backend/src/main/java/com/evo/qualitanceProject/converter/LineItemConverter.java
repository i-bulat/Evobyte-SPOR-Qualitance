package com.evo.qualitanceProject.converter;

import com.evo.qualitanceProject.dto.LineItemDto;
import com.evo.qualitanceProject.model.LineItem;
import com.evo.qualitanceProject.repository.OrderRepository;
import com.evo.qualitanceProject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LineItemConverter extends BaseConverter<LineItem, LineItemDto> {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public LineItemDto convertModelToDto(LineItem lineItem) {
        LineItemDto dto = new LineItemDto(lineItem.getOrder().getId(), lineItem.getProduct().getId(),
                lineItem.getQuantity());
        dto.setId(lineItem.getId());
        return dto;
    }

    @Override
    public LineItem convertDtoToModel(LineItemDto dto) {
        LineItem lineItem = new LineItem(orderRepository.findById(dto.getOrderId()).get(),
                productRepository.findById(dto.getProductId()).get(),
                dto.getQuantity());
        lineItem.setId(dto.getId());
        return lineItem;
    }
}
