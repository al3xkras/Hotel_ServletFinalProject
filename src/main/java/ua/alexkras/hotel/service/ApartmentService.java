package ua.alexkras.hotel.service;


import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ApartmentStatus;

import java.util.List;

public interface ApartmentService<Pageable,Entity> extends Service<Pageable, Entity> {

    List<Apartment> findAllApartments(Pageable pageable);
    List<Apartment> findApartmentsMatchingReservation(Reservation reservation, Pageable pageable);
    void updateApartment(Apartment apartment);
    void transactionalUpdateApartment(Apartment apartment);
    void updateApartmentStatusById(long id, ApartmentStatus status);
    void transactionalUpdateApartmentStatusById(long id, ApartmentStatus status);

}
