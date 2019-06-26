package com.optogo.repository;

import java.util.List;

public interface Repository<K, T> {
    /**
     * Saves the given entity
     * @param entity
     * @return saved entity
     */
    T save(T entity);

    /**
     * Updates the given entity
     * @param entity
     * @return updated entity
     */
    T update(T entity);

    /**
     * Returns all entities
     */
    List<T> findAll();

    /**
     * Returns the entity identified by the given ID
     * @param id - given ID
     * @return
     */
    T findById(K id);

    /**
     * Deletes the given entity
     * @param entity
     * @return
     */
    void delete(T entity);

    /**
     * Indicates whether an entity with the given ID exists.
     * @param id
     * @return
     */
    boolean existsById(Long id);
}
