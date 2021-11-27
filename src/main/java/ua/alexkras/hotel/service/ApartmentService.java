package ua.alexkras.hotel.service;


import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ApartmentStatus;

import java.sql.Connection;
import java.util.List;

public interface ApartmentService<Pageable> extends Service<Apartment> {

    List<Apartment> findAllApartments(Pageable pageable);
    List<Apartment> findApartmentsMatchingReservation(Reservation reservation, Pageable pageable);
    void updateApartment(Apartment apartment);
    void updateApartmentStatusById(long id, ApartmentStatus status);
    void transactionalUpdateApartmentStatusById(long id, ApartmentStatus status, Connection connection);
    int getApartmentsCount();
    int getApartmentsMatchingReservationCount(Reservation reservation);
    void transactionalUpdateApartment(Apartment apartment, Connection connection);
}
