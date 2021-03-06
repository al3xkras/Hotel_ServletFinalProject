package ua.alexkras.hotel.dao;

import ua.alexkras.hotel.entity.User;

import java.util.Optional;

public interface UserDAO extends GenericDao<User> {
    Optional<User> findById(long id);
    Optional<User> findByUsername(String username);
}
