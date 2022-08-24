package com.evo.qualitanceProject.converter;

import com.evo.qualitanceProject.dto.ProductDto;
import com.evo.qualitanceProject.model.Product;
import com.evo.qualitanceProject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter extends BaseConverter<Product, ProductDto> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDto convertModelToDto(Product product) {
        ProductDto productDto = new ProductDto(product.getName(), product.getImageURL(), product.getDescription(), product.getPrice());
        productDto.setId(product.getId());
        if (product.getQuantity() > 0) {
            productDto.setInStock("Yes");
        } else {
            productDto.setInStock("No");
        }
        return productDto;
    }

    @Override
    public Product convertDtoToModel(ProductDto dto) {
        return null;
    }
}
