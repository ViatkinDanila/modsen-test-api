package com.modsen.core.service.validator;

public interface Validator<T> {
    boolean isValid(T entity);
}
