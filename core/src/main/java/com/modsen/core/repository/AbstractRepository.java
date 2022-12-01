package com.modsen.core.repository;

import java.util.List;


import java.util.Optional;

public interface AbstractRepository<T> {
    T create(T entity);
    Optional<T> findById(long id);
    List<T> findAll();
    T update(T entity);
    void delete(T entity);
}
