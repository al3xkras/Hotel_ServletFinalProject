package ua.alexkras.hotel.dao.impl;

import ua.alexkras.hotel.dao.ApartmentDao;
import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.ApartmentStatus;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JDBCApartmentDao implements ApartmentDao {

    private final Connection connection;

    public JDBCApartmentDao(Connection connection) {
        this.connection = connection;
    }

    Optional<Apartment> findApartmentById(int id){
        return null;
    }

    //@Query("update Apartment apartment set apartment.status =:apartmentStatus where apartment.id =:id")
    void updateApartmentStatusById(int id, ApartmentStatus apartmentStatus){

    }

    List<Apartment> findApartmentsByApartmentClassAndPlacesAndStatus(
            ApartmentClass apartmentClass, int places, ApartmentStatus apartmentStatus){
        return null;
    }

    @Override
    public void create(Apartment entity) {

    }

    @Override
    public Optional<Apartment> findById(long id) {
        return null;
    }

    @Override
    public List<Apartment> findAll() {
        return null;
    }

    @Override
    public void update(Apartment entity) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void close() {

    }
}
