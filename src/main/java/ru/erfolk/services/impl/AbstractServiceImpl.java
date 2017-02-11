package ru.erfolk.services.impl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.erfolk.entities.BaseEntity;
import ru.erfolk.services.AbstractService;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by eugene on 31/08/16.
 */
@Slf4j
@NoArgsConstructor
abstract class AbstractServiceImpl<T extends BaseEntity<K>, K extends Serializable> implements AbstractService<T, K> {

    protected abstract CrudRepository<T, K> getRepository();

    @Override
    public Iterable<T> findAll() {
        return getRepository().findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<T> findAll(Iterable<K> ids) {
        List<T> result = new ArrayList<>();
        for (K id : ids) {
            result.add(findById(id));
        }
        return result;
    }

    //    @Loggable
    @Transactional(readOnly = true)
    @Override
    public T findById(@NotNull K id) {
        log.debug("find entity by id {}", id);
        log.warn("repository is {}", getRepository());
        return getRepository().findOne(id);
    }

    //    @Loggable
    @Transactional(readOnly = false)
    @Override
    public K create(@NotNull T entity) {
        if (entity.getId() != null) {
            throw new IllegalArgumentException("Entity has been already stored. Can't create");
        }
        return getRepository().save(entity).getId();
    }

    //    @Loggable
    @Transactional(readOnly = false)
    @Override
    public void update(@NotNull T entity) {
        if (entity.getId() == null) {
            throw new IllegalArgumentException("Entity is a new one. Can't update");
        }
        getRepository().save(entity);
    }

    //    @Loggable
    @Transactional(readOnly = false)
    @Override
    public boolean delete(@NotNull K id) {
        if (getRepository().exists(id)) {
            getRepository().delete(id);
            return true;
        } else {
            return false;
        }
    }
}
