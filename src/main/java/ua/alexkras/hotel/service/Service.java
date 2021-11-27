package ua.alexkras.hotel.service;

import java.util.Optional;

public interface Service <Entity> {
    void create(Entity entity);

    Optional<Entity> findById(long id);
}
