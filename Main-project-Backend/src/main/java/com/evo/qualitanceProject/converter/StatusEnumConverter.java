package com.evo.qualitanceProject.converter;

import com.evo.qualitanceProject.model.OrderStatusEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class StatusEnumConverter implements AttributeConverter<OrderStatusEnum, String> {
    @Override
    public String convertToDatabaseColumn(OrderStatusEnum status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    @Override
    public OrderStatusEnum convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(OrderStatusEnum.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
