package ua.alexkras.hotel.service;

import ua.alexkras.hotel.entity.User;

import java.util.Optional;

public interface UserService<Pageable> extends Service<Pageable, User>{

    Optional<User> findByUsername(String username);
}
