package com.example.paypalms.service;

import java.util.Collection;

public interface BaseService<T> {
    Collection<T> findAll();
    T save(T entity);
    T findById(long id);
    void delete(T entity);
    void deleteById(Long id);
    T update(T entity);
}
