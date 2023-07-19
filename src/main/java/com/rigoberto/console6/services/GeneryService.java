package com.rigoberto.console6.services;

import org.springframework.data.domain.Page;

import java.util.Optional;

public interface GeneryService<T, K> {
    Iterable<T> findAll();
    Page<T> findAll(Integer page);
    Optional<T> findById(K id);
    T save(T entity);
    void deleteById(K id);
}
