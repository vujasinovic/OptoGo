package com.optogo.repository;

public interface ReadOnlyRepository<K, T> extends Repository<K, T> {

    @Override
    default T save(T entity) {
        throw new UnsupportedOperationException("Not Allowed in ReadOnlyRepository.");
    }

    @Override
    default T update(T entity) {
        throw new UnsupportedOperationException("Not Allowed in ReadOnlyRepository.");
    }

    @Override
    default void delete(T entity) {
        throw new UnsupportedOperationException("Not Allowed in ReadOnlyRepository.");
    }

}
