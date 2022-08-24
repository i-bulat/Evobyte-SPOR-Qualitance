package com.evo.qualitanceProject.dto;



import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class ProductPage {
    private List<ProductDto> productDtoList;
    private int totalPages;
    private long totalProducts;
}
