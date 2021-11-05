package ua.alexkras.hotel.dao;

import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.ApartmentStatus;

import java.util.List;

public interface ApartmentDao extends GenericDao<Apartment> {

    void updateApartmentStatusById(long id, ApartmentStatus apartmentStatus);

    List<Apartment> findApartmentsByApartmentClassAndPlacesAndStatus(
            ApartmentClass apartmentClass,
            int places,
            ApartmentStatus apartmentStatus,
            int start, int total);

}
