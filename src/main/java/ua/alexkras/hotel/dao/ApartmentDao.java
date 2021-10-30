package ua.alexkras.hotel.dao;

import ua.alexkras.hotel.entity.Apartment;

public interface ApartmentDao extends GenericDao<Apartment> {
    void create(long id, Apartment apartment);
}
