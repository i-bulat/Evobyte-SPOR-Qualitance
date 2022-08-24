package com.evo.qualitanceProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto extends BaseDto {
    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String imageUrl;

}
