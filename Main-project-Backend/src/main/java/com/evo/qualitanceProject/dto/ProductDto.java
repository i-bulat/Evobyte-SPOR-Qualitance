package com.evo.qualitanceProject.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductDto extends BaseDto{
    private String name;
    private String imageUrl;
    private String description;
    private long price;

    private String inStock;


    public ProductDto(String name, String imageUrl, String description, long price) {

        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.price = price;
    }


}
