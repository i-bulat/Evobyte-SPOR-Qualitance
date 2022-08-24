package com.evo.qualitanceProject.converter;

import com.evo.qualitanceProject.dto.UserDto;
import com.evo.qualitanceProject.model.AppUser;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends BaseConverter<AppUser, UserDto> {
    @Override
    public UserDto convertModelToDto(AppUser user) {
        UserDto dto = UserDto.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .imageUrl(user.getImageURL())
                .build();
        dto.setId(user.getId());
        return dto;
    }

    @Override
    public AppUser convertDtoToModel(UserDto dto) {
        throw new RuntimeException("not yet implemented.");
    }
}
