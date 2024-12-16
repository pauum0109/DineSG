package session.utils;


import java.util.List;
import java.util.Optional;

public interface DAO<T, E> {
    Optional<List<T>> findAll();

    Optional<T> findById(E id);

    void save(T entity);

    void update(T entity);

    void delete(E entity);
}