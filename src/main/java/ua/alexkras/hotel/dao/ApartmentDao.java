package ua.alexkras.hotel.dao;

import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.ApartmentStatus;

import java.util.List;
import java.util.Optional;

public interface ApartmentDao extends GenericDao<Apartment> {
    void create(long id, Apartment apartment);

    Optional<Apartment> findApartmentById(long id);

    void updateApartmentStatusById(long id, ApartmentStatus apartmentStatus);

    List<Apartment> findApartmentsByApartmentClassAndPlacesAndStatus(
            ApartmentClass apartmentClass,
            int places,
            ApartmentStatus apartmentStatus);

}
