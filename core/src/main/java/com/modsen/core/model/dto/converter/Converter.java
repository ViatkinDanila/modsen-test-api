package com.modsen.core.model.dto.converter;

public interface Converter<E, D> {
    E convertToEntity(D dto);
    D convertToDto(E entity);
}
