package com.evo.qualitanceProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReviewDto extends BaseDto {
    private String title;

    private String comment;

    private Integer rating;

    private LocalDate dateCreated;

    private String username;
}
