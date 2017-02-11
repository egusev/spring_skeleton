package ru.erfolk.services;


import ru.erfolk.entities.BaseEntity;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by eugene on 31/08/16.
 */
public interface AbstractService<T extends BaseEntity<K>, K extends Serializable> {
    Iterable<T> findAll();

    Collection<T> findAll(Iterable<K> ids);

    T findById(K id);

    K create(T entity);

    void update(T entity);

    boolean delete(K id);
}
