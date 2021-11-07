package ua.alexkras.hotel.service;

import java.util.Optional;

public interface UserService<Pageable,Entity> extends Service<Pageable,Entity>{

    Optional<Entity> findByUsername(String username);
}
