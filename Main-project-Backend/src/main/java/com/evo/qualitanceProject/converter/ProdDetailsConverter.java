package com.evo.qualitanceProject.converter;

import com.evo.qualitanceProject.dto.ProdDetailsDto;
import com.evo.qualitanceProject.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProdDetailsConverter extends BaseConverter<Product, ProdDetailsDto> {
    @Override
    public ProdDetailsDto convertModelToDto(Product product) {
        ProdDetailsDto dto = ProdDetailsDto.builder()
                .title(product.getName())
                .price(product.getPrice())
                .imageURL(product.getImageURL())
                .category(product.getSubCategory().getProductCategory().getName())
                .subcategory(product.getSubCategory().getName())
                .description(product.getDescription())
                .build();
        dto.setId(product.getId());
        return dto;
    }

    @Override
    public Product convertDtoToModel(ProdDetailsDto dto) {
        throw new RuntimeException("not yet implemented.");
    }
}
