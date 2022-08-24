package com.evo.qualitanceProject.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProdDetailsDto extends BaseDto {
    private String title;
    private Long price;
    private String imageURL;
    private String category;
    private String subcategory;
    private String description;
}
