package com.evo.qualitanceProject.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LineItemDto extends BaseDto {
    private Long orderId;

    private Long productId;

    private int quantity;
}
