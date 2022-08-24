package com.evo.qualitanceProject.converter;

import com.evo.qualitanceProject.dto.BaseDto;
import com.evo.qualitanceProject.model.BaseEntity;

public interface Converter<Model extends BaseEntity<Long>, Dto extends BaseDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);
}
