package ua.alexkras.hotel.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable {
    void create (T entity);
    Optional<T> findById(long id);
    List<T> findAll(int start, int total);
    void update(T entity);
    void delete(long id);
    void close();
    void commit();
    void rollback();
}
