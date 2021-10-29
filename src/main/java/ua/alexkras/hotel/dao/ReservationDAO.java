package ua.alexkras.hotel.dao;

import ua.alexkras.hotel.entity.Reservation;

import java.util.Optional;

public interface ReservationDAO extends GenericDao<Reservation> {
    Optional<Reservation> findById(long id);
}
